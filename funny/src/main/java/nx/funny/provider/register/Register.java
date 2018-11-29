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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

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

    @Setter
    private ServiceProviderScanner scanner;

    public Register() {
        typeTargetFactoryMap = new ConcurrentHashMap<>();
        scanner = new DefaultServiceProviderScanner();
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

    /**
     * 扫描一个包下所有的服务类并注册
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
                if (canRegisterService(serviceClass)) {
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
     * 注册一个服务提供者,service必须被@ServiceProvider标注
     *
     * @param service 要注册的服务
     * @param factory 服务工厂
     */
    public void register(Class<?> service, ServiceTargetFactory factory) {
        if (service.getAnnotation(ServiceProvider.class) == null)
            return;
        String[] names = resolveNames(service);
        register(names[0], names[1], factory);
    }

    /**
     * 根据指定的服务接口类型注册一个服务提供者
     *
     * @param interFace 服务接口类型
     * @param service   要注册的服务提供者对象
     */
    public void register(Class<?> interFace, Object service) {
        if (interFace.isInterface()) {
            register(interFace.getName(), service.getClass().getName(), t -> service);
        }
    }

    private boolean canRegisterService(Class<?> service) {
        return !service.isInterface() &&
                service.getInterfaces().length > 0 &&
                service.isAnnotationPresent(ServiceProvider.class);
    }

    private String[] resolveNames(Class<?> serviceClazz) {
        // 默认取第一个接口的名称
        String name = serviceClazz.getInterfaces()[0].getName();
        String typeName = serviceClazz.getName();
        ServiceProvider serviceProvider = serviceClazz.getAnnotation(ServiceProvider.class);
        if (!serviceProvider.interFace().equals(ServiceProvider.class)) {
            name = serviceProvider.interFace().getName();
        }
        return new String[]{name, typeName};
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
