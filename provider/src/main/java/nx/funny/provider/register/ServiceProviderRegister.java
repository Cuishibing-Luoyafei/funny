package nx.funny.provider;

import lombok.Getter;
import lombok.Setter;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析服务提供者的信息，并注册信息到注册中心
 * 如果要注册的类有ServiceProvider注解，则优先使用注解的信息
 *
 * 该类应该为单例模式
 */
public class ServiceProviderRegister {

    @Getter
    @Setter
    private ServicePositionProvider positionProvider;

    @Getter
    @Setter
    private ServiceRegistry serviceRegistry;

    private Map<Class<?>,ServiceTargetFactory> typeTargetFactoryMap;

    public ServiceProviderRegister() {
        typeTargetFactoryMap = new ConcurrentHashMap<>();
    }

    public ServiceProviderRegister(ServicePositionProvider positionProvider,
                                   ServiceRegistry serviceRegistry) {
        this();
        this.positionProvider = positionProvider;
        this.serviceRegistry = serviceRegistry;
    }

    public <T> void register(Class<T> clazz,ServiceTargetFactory<T> targetFactory) {
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
        register(name, typeName);
        typeTargetFactoryMap.put(clazz,targetFactory);
    }

    private void register(String name, String typeName) {
        ServiceType type = new ServiceType(name, typeName);
        ServicePosition position = new ServicePosition(positionProvider.getIp(),
                positionProvider.getPort());
        ServiceInfo info = new ServiceInfo(type, position);
        serviceRegistry.register(info);
    }

}
