package nx.funny.provider.register;

import lombok.Setter;
import nx.funny.provider.ServicePositionProvider;
import nx.funny.provider.ServiceProvider;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析服务提供者的信息，并注册信息到注册中心
 * 如果要注册的类有ServiceProvider注解，则优先使用注解的信息
 * <p>
 * 该类应该为单例模式
 */
public class ServiceProviderRegister {

    @Setter
    private ServicePositionProvider positionProvider;

    @Setter
    private ServiceRegistry serviceRegistry;

    @Setter
    private Map<String, ServiceTargetFactory> typeTargetFactoryMap;

    public ServiceProviderRegister() {
        typeTargetFactoryMap = new ConcurrentHashMap<>();
    }

    public ServiceProviderRegister(ServicePositionProvider positionProvider,
                                   ServiceRegistry serviceRegistry) {
        this();
        this.positionProvider = positionProvider;
        this.serviceRegistry = serviceRegistry;
    }

    public void register(Class<?> type, ServiceTargetFactory targetFactory) {
        if (type == null)
            return;
        String name = type.getName();
        String typeName = name;
        ServiceProvider serviceProvider = type.getAnnotation(ServiceProvider.class);
        if (serviceProvider != null) {
            String n = serviceProvider.name();
            String t = serviceProvider.typeName();
            if (n != null)
                name = n;
            if (t != null)
                typeName = t;
        }
        register(name, typeName);
        typeTargetFactoryMap.put(typeName, targetFactory);
    }

    private void register(String name, String typeName) {
        ServiceType type = new ServiceType(name, typeName);
        ServicePosition position = new ServicePosition(positionProvider.getIp(),
                positionProvider.getPort());
        ServiceInfo info = new ServiceInfo(type, position);
        serviceRegistry.register(info);
    }

    /**
     * 根据实现类名称获取对应的targetFactory
     */
    public ServiceTargetFactory getTargetFactory(String typeName) {
        return typeTargetFactoryMap.get(typeName);
    }

    /**
     * 注册相应类型的targetFactory
     */
    public void registerTargetFactory(String typeName, ServiceTargetFactory factory) {
        typeTargetFactoryMap.put(typeName, factory);
    }
}
