package nx.funny.registry;

import jdk.nashorn.internal.ir.WhileNode;
import lombok.NoArgsConstructor;
import nx.funny.transporter.common.HeartBeatRequestCodeType;
import nx.funny.transporter.common.HeartBeatResponseCodeType;
import nx.funny.transporter.message.HeartBeatRequest;
import nx.funny.transporter.message.HeartBeatResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.SetUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * ServiceRegistry的服务端实现
 */
@NoArgsConstructor
public class ServerServiceHeapRegistry implements ServiceRegistry {

    private Logger logger = Logger.getLogger(getClass().getName());

    private final ConcurrentHashMap<ServiceType, Set<ServicePosition>> serviceRegistry = new ConcurrentHashMap<>();

    private static final Integer CHECK_INTERVAL = 1000;

    private final Map<Integer, Set<HeartBeatRequest>> allHeartbeatInfoMap = new ConcurrentHashMap<>();

    private final Map<Integer, ThreadPoolExecutor> serviceNamePoolMap = new ConcurrentHashMap<>();

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = structurePool();

    private static ThreadPoolExecutor structurePool() {
        // 使用无界队列, 提高容错性
        return new ThreadPoolExecutor(100, 100, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.AbortPolicy());
    }

    private Set<ServicePosition> getPositionContainer(ServiceType type) {
        return serviceRegistry.computeIfAbsent(type, t -> new HashSet<>());
    }

    @Override
    public void register(ServiceInfo info) {
        ServiceType type = info.getType();
        ServicePosition position = info.getPosition();

        Set<ServicePosition> container = getPositionContainer(type);
        synchronized (container) {
            container.add(position);
            logger.log(Level.INFO, "register:" + info.toString());
        }
    }

    @Override
    public void remove(ServiceInfo info) {
        Set<ServicePosition> container = getPositionContainer(info.getType());
        synchronized (container) {
            container.remove(info.getPosition());
        }
        logger.log(Level.INFO, "remove:" + info.toString());
    }

    @Override
    public void removeAll(ServiceType type) {
        serviceRegistry.remove(type);
        logger.log(Level.INFO, "removeAll:" + type.toString());
    }

    @Override
    public Set<ServiceInfo> retrieve(String name) {
        logger.log(Level.INFO, "retrieve:" + name);
        return serviceRegistry
                .entrySet()
                .stream()
                .filter(entry -> name.equals(entry.getKey().getName()))
                .flatMap(entry -> entry.getValue().stream().map(position -> new ServiceInfo(entry.getKey(), position)))
                .collect(Collectors.toSet());
    }

    @Override
    public HeartBeatResponse receiveHeartbeat(Set<HeartBeatRequest> services) {
        logger.info(String.format("receiveHeartbeat: %s", services));
        THREAD_POOL_EXECUTOR.execute(() -> {
            services.stream().forEach(heartBeatRequest -> {
                // 通过name确定一个服务池 (将字符串转换为Integer, 降低内存占用)
                final Integer threadIndex = heartBeatRequest.getServiceInfo().getType().getName().hashCode();

                if (serviceNamePoolMap.get(threadIndex) == null) {
                    synchronized (serviceNamePoolMap) {
                        serviceNamePoolMap.putIfAbsent(threadIndex, structurePool());
                    }
                }

                synchronized (serviceNamePoolMap.get(threadIndex)) {
                    // 在服务池内部处理同一个类型的服务, 遍历处理, 并交由其内部的线程池, 进行每个心跳的维持
                    serviceNamePoolMap.get(threadIndex).execute(() -> {
                        if (HeartBeatRequestCodeType.HeartBeatRequestCodeTypeValue.NORMAL.value == heartBeatRequest.getCode()) {
                            allHeartbeatInfoMap.putIfAbsent(threadIndex, new HashSet<>(0));
                            if (allHeartbeatInfoMap.get(threadIndex).contains(heartBeatRequest)) {
                                allHeartbeatInfoMap.get(threadIndex).remove(heartBeatRequest);
                                allHeartbeatInfoMap.get(threadIndex).add(heartBeatRequest);
                            } else {
                                allHeartbeatInfoMap.get(threadIndex).add(heartBeatRequest);
                                // 进行心跳处理
                                checkHeartbeatItem(heartBeatRequest);
                            }
                        } else if (HeartBeatRequestCodeType.HeartBeatRequestCodeTypeValue.FIN.value == heartBeatRequest.getCode()) {
                            // 移除注册的信息
                            remove(heartBeatRequest.getServiceInfo());
                            allHeartbeatInfoMap.get(threadIndex).remove(heartBeatRequest);
                        } else {
                            // TODO: 2018/12/13 处理非心跳code的情况
                            logger.warning(String.format("not normal heartbeat: %s", heartBeatRequest));
                        }
                    });
                }
            });
        });
        return HeartBeatResponse.of()
                .code(HeartBeatResponseCodeType.HeartBeatResponseCodeTypeValue.OK.value)
                .currentTime(new Long(System.currentTimeMillis() / 1000).intValue());
    }

    private void checkHeartbeatItem(final HeartBeatRequest heartBeatRequest) {
        while (true) {
            if (System.currentTimeMillis() > heartBeatRequest.getSendInterval() + (long) heartBeatRequest.getCurrentTime() * 1000 + (HeartBeatRequest.FAILURE_RETRY_TIME * HeartBeatRequest.FAILURE_RETRY_INTERVAL)) {
                logger.warning(String.format("provider heartbeat timeout, provider info: %s", heartBeatRequest));
                // 移除注册的信息
                remove(heartBeatRequest.getServiceInfo());
                allHeartbeatInfoMap.getOrDefault(heartBeatRequest.getServiceInfo().getType().getName().hashCode(), new HashSet<>(0)).remove(heartBeatRequest);
                break;
            }
            try {
                Thread.sleep(CHECK_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void register(List<ServiceInfo> services) {
        if (services == null)
            return;
        services.forEach(this::register);
    }
}
