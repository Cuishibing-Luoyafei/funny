package nx.funny.consumer;

import lombok.Getter;
import lombok.Setter;
import nx.funny.provider.ServicePositionProvider;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class DefaultProxyFactory implements ProxyFactory {

    @Getter
    @Setter
    private ServiceRegistry serviceRegistry;

    @Setter
    private ServiceChooser serviceChooser;

    private Map<Class<?>, Object> nameProxyCache = new HashMap<>();

    public DefaultProxyFactory() {
        serviceChooser = new RandomServiceChooser();
    }

    public DefaultProxyFactory(String registryIP, int registryPort, Class<? extends ServiceRegistry> registryType) {
        this();
        generateRegsitry(() -> new ServicePosition(registryIP, registryPort), registryType);
    }

    /**
     * 根据注册中心的位置和类型生成实例对象并设置到代理工厂中
     *
     * @param positionProvider 注册中心位置信息
     * @param registryType     注册中心类型,由于可能有多种注册中心类型,所以这里要指定
     * @return 生成的注册中心实例
     */
    public ServiceRegistry generateRegsitry(ServicePositionProvider positionProvider,
                                            Class<? extends ServiceRegistry> registryType) {
        ServiceRegistry registry = getProxy(ServiceRegistry.class,
                new ServiceInfo(
                        new ServiceType(ServiceRegistry.class.getName(),
                                registryType.getTypeName()),
                        positionProvider.getServicePosition()
                )
        );
        this.serviceRegistry = registry;
        return registry;
    }

    @Override
    public <T> T getProxy(Class<T> clazz) {
        return getProxy(clazz.getName(), clazz);
    }

    private <T> T getProxy(String name, Class<T> clazz) {
        return (T) nameProxyCache.computeIfAbsent(clazz, s -> {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{s}, new DefaultInvocationHandler(name,
                            serviceRegistry, serviceChooser));
        });
    }

    @Override
    public <T> T getProxy(Class<T> clazz, ServiceInfo info) {
        return (T) nameProxyCache.computeIfAbsent(clazz, s -> {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{s}, new SpecificInvocationHandler(info));
        });
    }

}
