package nx.funny.provider.register;

import lombok.Getter;
import lombok.Setter;
import nx.funny.consumer.DefaultProxyFactory;
import nx.funny.consumer.ProxyFactory;
import nx.funny.provider.ServicePositionProvider;
import nx.funny.provider.ServiceProvider;
import nx.funny.provider.scanner.DefaultServiceProviderScanner;
import nx.funny.provider.scanner.ServiceProviderScanner;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;
import nx.funny.transporter.common.HeartBeatRequestCodeType;
import nx.funny.transporter.exception.HeartbeatException;
import nx.funny.transporter.message.HeartBeatRequest;
import nx.funny.transporter.message.HeartBeatResponse;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 解析服务提供者的信息，并注册信息到注册中心 如果要注册的类有ServiceProvider注解，则优先使用注解的信息
 * <p>
 * 该类应该为单例模式
 */
public class ServiceRegister {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Getter
    @Setter
    private ServicePositionProvider positionProvider;

    @Setter
    private ServiceRegistry serviceRegistry;

    @Setter
    private Map<ServiceType, ServiceTargetFactory> typeTargetFactoryMap;

    @Setter
    private ServiceProviderScanner scanner;

    private boolean isRegistry;

    public ServiceRegister(boolean isRegistry) {
        this.typeTargetFactoryMap = new ConcurrentHashMap<>();
        this.scanner = new DefaultServiceProviderScanner();
        this.isRegistry = isRegistry;
    }

    public ServiceRegister(ServicePositionProvider positionProvider, ServiceRegistry serviceRegistry, boolean isRegistry) {
        this(isRegistry);
        this.positionProvider = positionProvider;
        this.serviceRegistry = serviceRegistry;
    }

    public ServiceRegister(String registryIp, int registryPort, Class<? extends ServiceRegistry> registryType,
                           String providerIp, int providerPort, boolean isRegistry) {
        this(isRegistry);
        ProxyFactory proxyFactory = new DefaultProxyFactory(registryIp, registryPort, registryType);
        serviceRegistry = proxyFactory.getServiceRegistry();
        positionProvider = () -> new ServicePosition(providerIp, providerPort);
    }

    /**
     * 扫描一个包下所有的有ServiceProvider注解的服务类并注册
     *
     * @param basePackage  要扫描的包名
     * @param excludeClass 不要注册的类
     */
    public void scan(ServiceTargetFactory factory, String basePackage, String... excludeClass) {
        List<String> targetClassNames = scanner.scan(basePackage, excludeClass);
        List<Class<?>> targetClasses = new ArrayList<>(targetClassNames.size());
        targetClassNames.forEach(name -> {
            try {
                Class<?> serviceClass = Class.forName(name);
                if (canRegisterService(serviceClass) && serviceClass.isAnnotationPresent(ServiceProvider.class)) {
                    targetClasses.add(serviceClass);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        register(targetClasses, factory);
    }

    public void register(List<Class<?>> services, ServiceTargetFactory factory) {
        if (services == null)
            return;
        services.forEach(s -> {
            register(s, factory);
        });
    }

    /**
     * 注册一个服务提供者 serviceType必须是服务的实现类，否则会忽略该服务的注册 如果服务没有实现接口则会忽略该服务的注册
     *
     * @param serviceType 服务实现类
     * @param factory     服务提供者工厂
     */
    public void register(Class<?> serviceType, ServiceTargetFactory factory) {
        if (!canRegisterService(serviceType))
            return;
        String[] names = resolveNames(serviceType);
        register(names[0], names[1], factory);
    }

    /**
     * 注册一个服务 根据服务对象实现类解析服务名称
     *
     * @param service 要注册的服务
     */
    public void register(Object service) {
        register(service.getClass(), service);
    }

    /**
     * 注册一个服务提供者 如果serviceType是接口，则服务名称是该接口的名称，否则根据服务对象实现类解析服务名称
     *
     * @param serviceType 服务接口类型或实现类
     * @param service     要注册的服务提供者对象
     */
    public void register(Class<?> serviceType, Object service) {
        if (serviceType.isInterface()) {
            register(serviceType.getName(), service.getClass().getName(), t -> service);
        } else {
            if (!canRegisterService(serviceType))
                return;
            String[] names = resolveNames(serviceType);
            register(names[0], names[1], t -> service);
        }
    }

    private boolean canRegisterService(Class<?> service) {
        return !service.isInterface() && service.getInterfaces().length > 0;
    }

    private String[] resolveNames(Class<?> serviceClazz) {
        // 默认取第一个接口的名称
        String name = serviceClazz.getInterfaces()[0].getName();
        String typeName = serviceClazz.getName();
        ServiceProvider serviceProvider = serviceClazz.getAnnotation(ServiceProvider.class);
        if (serviceProvider != null && !serviceProvider.interFace().equals(ServiceProvider.class)) {
            name = serviceProvider.interFace().getName();
        }
        return new String[] { name, typeName };
    }

    private void register(String name, String typeName, ServiceTargetFactory targetFactory) {
        ServiceType type = new ServiceType(name, typeName);
        ServicePosition position = positionProvider.getServicePosition();
        ServiceInfo info = new ServiceInfo(type, position);
        waitRegisterInfos.add(info);
        typeTargetFactoryMap.put(type, targetFactory);
    }

    private List<ServiceInfo> waitRegisterInfos = new ArrayList<>();

    /**
     * 把缓存的服务信息注册到注册中心，这样可以避免频繁的网络通信
     */
    public synchronized void syncData() {
        if (serviceRegistry != null)
            serviceRegistry.register(waitRegisterInfos);
        if (!isRegistry)
            heartbeat(waitRegisterInfos);
        waitRegisterInfos.clear();
    }

    volatile boolean HEARTBEAT = true;
    volatile boolean RUNNING = false;

    private ExecutorService heartBeatExecutorService = Executors.newFixedThreadPool(1);

    // Treat the map as a set
    private Map<HeartBeatRequest, Object> allBeatRequests = new ConcurrentHashMap<>();
    private final Object EMPTY_OBJECT = new Object();

    private void heartbeat(List<ServiceInfo> needHeartBeatInfos) {
        needHeartBeatInfos.stream()
                .map(info -> HeartBeatRequest.of()
                        .code(HeartBeatRequestCodeType.HeartBeatRequestCodeTypeValue.NORMAL.value)
                        .currentTime((int) System.currentTimeMillis() / 1000)
                        .sendInterval(HeartBeatRequest.HREATBEAT_INTERVAL).serviceInfo(info))
                .collect(Collectors.toSet()).stream().map(request -> {
                    return allBeatRequests.put(request, EMPTY_OBJECT);
                });
        if (!RUNNING) {
            RUNNING = true;
            heartBeatExecutorService.submit(() -> {
                Integer retryTime = HeartBeatRequest.FAILURE_RETRY_TIME;
                while (HEARTBEAT && !Thread.interrupted()) {
                    try {
                        final HeartBeatResponse response = serviceRegistry.receiveHeartbeat(allBeatRequests.keySet());
                        logger.info(String.format("registry response: %s", response));
                        retryTime = HeartBeatRequest.FAILURE_RETRY_TIME;
                        Thread.sleep(HeartBeatRequest.HREATBEAT_INTERVAL);
                    } catch (Exception e) {
                        try {
                            if (retryTime <= 0) {
                                logger.warning(String.format("registry response err, retry failure! event: %s", e));
                                throw new HeartbeatException("registry response err, retry failure!", e);
                            } else {
                                logger.warning(String.format("registry response err, retrying! event: %s", e));
                            }
                            Thread.sleep(HeartBeatRequest.FAILURE_RETRY_INTERVAL);
                            retryTime--;
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public void offline(ServiceInfo serviceInfo) {
        HEARTBEAT = false;
        positionProvider.getServicePosition();
        Set<HeartBeatRequest> heartBeatRequestSet = new HashSet<>(1);
        heartBeatRequestSet.add(HeartBeatRequest.of()
                .code(HeartBeatRequestCodeType.HeartBeatRequestCodeTypeValue.FIN.value).serviceInfo(serviceInfo));
        HeartBeatResponse heartBeatResponse = serviceRegistry.receiveHeartbeat(heartBeatRequestSet);
        logger.info(String.format("offline response:%s", heartBeatResponse));
    }

    /**
     * 根据实现类名称获取对应的targetFactory
     */
    public ServiceTargetFactory getTargetFactory(ServiceType type) {
        return typeTargetFactoryMap.get(type);
    }

}
