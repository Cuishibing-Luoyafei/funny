package nx.funny.provider.register;

import lombok.Getter;
import lombok.Setter;
import nx.funny.consumer.DefaultProxyFactory;
import nx.funny.consumer.ProxyFactory;
import nx.funny.provider.ServicePositionProvider;
import nx.funny.provider.ServiceProvider;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析服务提供者的信息，并注册信息到注册中心
 * 如果要注册的类有ServiceProvider注解，则优先使用注解的信息
 * <p>
 * 该类应该为单例模式
 */
public class Register {

    @Getter
    @Setter
    private ServicePositionProvider positionProvider;

    @Setter
    private ServiceRegistry serviceRegistry;

    @Setter
    private Map<ServiceType, ServiceTargetFactory> typeTargetFactoryMap;

    public Register() {
        typeTargetFactoryMap = new ConcurrentHashMap<>();
    }

    public Register(ServicePositionProvider positionProvider,
                    ServiceRegistry serviceRegistry) {
        this();
        this.positionProvider = positionProvider;
        this.serviceRegistry = serviceRegistry;
    }

    public Register(String registryIp, int registryPort, Class<? extends ServiceRegistry> registryType,
                    String providerIp, int providerPort) {
        this();
        ProxyFactory proxyFactory = new DefaultProxyFactory(registryIp, registryPort, registryType);
        serviceRegistry = proxyFactory.getServiceRegistry();
        positionProvider = () -> new ServicePosition(providerIp, providerPort);
    }

    public void register(List<Class<?>> types) {
        if (types == null)
            return;
        types.forEach(this::register);
    }

    /**
     * 注册一个服务提供者，如果类型上有ServiceProvider注解就用注解的信息，否则就用类型本身的
     * 默认使用FirstNewTargetFactory对象工厂
     *
     * @param type 要注册的类型
     */
    public void register(Class<?> type) {
        String[] names = resolveNames(type);
        register(names[0], names[1], FirstNewTargetFactory.INSTANCE());
    }

    /**
     * 注册一个服务提供者，如果类型上有ServiceProvider注解就用注解的信息，否则就用类型本身的
     *
     * @param type          要注册的类型
     * @param targetFactory 提供具体服务提供者对象的工厂
     */
    public void register(Class<?> type, ServiceTargetFactory targetFactory) {
        String[] names = resolveNames(type);
        register(names[0], names[1], targetFactory);
    }

    /**
     * 根据指定的服务名称注册一个服务提供者
     *
     * @param name    服务名称
     * @param service 要注册的服务提供者对象
     */
    public void register(String name, Object service) {
        register(name, service.getClass().getName(), s -> service);
    }

    /**
     * 根据指定的服务名称注册一个服务提供者
     *
     * @param name     服务名称
     * @param typeName 类型名
     * @param service  要注册的服务提供者对象
     */
    public void register(String name, String typeName, Object service) {
        register(name, typeName, s -> service);
    }

    /**
     * 根据指定的服务类型注册一个服务提供者
     *
     * @param type    服务类型
     * @param service 要注册的服务提供者对象
     */
    public void register(Class<?> type, Object service) {
        String[] names = resolveNames(type);
        register(names[0], names[1], t -> service);
    }

    private String[] resolveNames(Class<?> type) {
        String name = type.getName();
        String typeName = name;
        ServiceProvider serviceProvider = type.getAnnotation(ServiceProvider.class);
        if (serviceProvider != null) {
            String n = serviceProvider.value();
            if ("".equals(n)) {
                n = serviceProvider.name();
            }
            if (!"".equals(n)) {
                n = type.getPackage().getName() + "." + n;
            }
            String t = serviceProvider.typeName();
            if (!"".equals(n))
                name = n;
            if (!"".equals(t)) {
                typeName = t;
            }
        }
        return new String[]{name, typeName};
    }

    private void register(String name, String typeName, ServiceTargetFactory targetFactory) {
        ServiceType type = new ServiceType(name, typeName);
        ServicePosition position = positionProvider.getServicePosition();
        ServiceInfo info = new ServiceInfo(type, position);
        // serviceRegistry.register(info);
        waitRegisterInfos.add(info);
        typeTargetFactoryMap.put(type, targetFactory);
    }

    private List<ServiceInfo> waitRegisterInfos = new ArrayList<>();

    /**
     * 把缓存的服务信息注册到注册中心，这样可以避免频繁的网络通信
     */
    public void syncData() {
        serviceRegistry.register(waitRegisterInfos);
        waitRegisterInfos.clear();
    }

    /**
     * 根据实现类名称获取对应的targetFactory
     */
    public ServiceTargetFactory getTargetFactory(ServiceType type) {
        return typeTargetFactoryMap.get(type);
    }

}
