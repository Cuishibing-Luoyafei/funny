package nx.funny.consumer;

import lombok.AllArgsConstructor;
import lombok.Setter;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServiceRegistry;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultInvocationHandler extends SpecificInvocationHandler {

    @Setter
    private String serviceName;

    @Setter
    private ServiceRegistry serviceRegistry;

    @Setter
    private ServiceChooser serviceChooser;

    /**
     * 服务信息缓存的时间，0为不缓存
     */
    @Setter
    private long retrieveInfoTimeInterval = 600000;

    private Map<String, RetrieveTimeServiceInfoGroup> serviceInfoCache = new ConcurrentHashMap<>();

    public DefaultInvocationHandler(String serviceName,
                                    ServiceRegistry serviceRegistry, ServiceChooser serviceChooser) {
        this.serviceName = serviceName;
        this.serviceRegistry = serviceRegistry;
        this.serviceChooser = serviceChooser;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Set<ServiceInfo> serviceInfos;
        if (retrieveInfoTimeInterval <= 0) {
            serviceInfos = serviceRegistry.retrieve(serviceName);
        } else {
            RetrieveTimeServiceInfoGroup retrieveTimeServiceInfoGroup = serviceInfoCache.get(serviceName);
            if (retrieveTimeServiceInfoGroup == null ||
                    System.currentTimeMillis() - retrieveTimeServiceInfoGroup.lastRetrieveTime > retrieveInfoTimeInterval) {
                serviceInfos = serviceRegistry.retrieve(serviceName);
                serviceInfoCache.put(serviceName, new RetrieveTimeServiceInfoGroup(System.currentTimeMillis(), serviceInfos));
            } else {
                serviceInfos = retrieveTimeServiceInfoGroup.serviceInfos;
            }
        }
        ServiceInfo serviceInfo = serviceChooser.choose(serviceInfos);
        return invoke(serviceInfo, method, args);
    }

    @AllArgsConstructor
    private static class RetrieveTimeServiceInfoGroup {
        long lastRetrieveTime = 0;
        Set<ServiceInfo> serviceInfos;
    }
}
