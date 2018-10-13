package nx.funny.provider;

import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;

/**
 * 解析服务提供者的信息，并注册信息到注册中心
 * 如果要注册的类有ServiceProvider注解，则优先使用注解的信息
 */
public class ServiceProviderRegister {

    private ServicePositionProvider positionProvider;

    private ServiceRegistry serviceRegistry;

    public ServiceProviderRegister() {
    }

    public ServiceProviderRegister(ServicePositionProvider positionProvider,
                                   ServiceRegistry serviceRegistry) {
        this.positionProvider = positionProvider;
        this.serviceRegistry = serviceRegistry;
    }

    public void register(Class<?> clazz) {
        if (clazz == null)
            return;
        String name = clazz.getName();
        String typeName = name;
        ServiceProvider serviceProvider = clazz.getAnnotation(ServiceProvider.class);
        if (serviceProvider != null) {
            String n = serviceProvider.name();
            String t = serviceProvider.typeName();
            if (n != null)
                name = n;
            if (t != null)
                typeName = t;
        }
        registerWithSelfInfo(name, typeName);
    }

    private void registerWithSelfInfo(String name, String typeName) {
        ServiceType type = new ServiceType(name, typeName);
        ServicePosition position = new ServicePosition(positionProvider.getIp(),
                positionProvider.getPort());
        ServiceInfo info = new ServiceInfo(type, position);
        serviceRegistry.register(info);
    }

    public void setPositionProvider(ServicePositionProvider positionProvider) {
        this.positionProvider = positionProvider;
    }

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
}
