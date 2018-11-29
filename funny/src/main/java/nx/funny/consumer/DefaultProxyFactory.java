package nx.funny.consumer;

import lombok.Getter;
import lombok.Setter;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class DefaultProxyFactory implements ProxyFactory {

    @Getter
    private ServiceRegistry serviceRegistry;

    @Setter
    private ServiceChooser serviceChooser;

    private Map<Class<?>, Object> nameProxyCache = new HashMap<>();

    public DefaultProxyFactory(String registryIp, int registryPort, Class<? extends ServiceRegistry> registryType) {
        serviceRegistry = getProxy(ServiceRegistry.class,
                new ServiceInfo(new ServiceType(ServiceRegistry.SERVICE_REGISTRY_NAME, registryType.getTypeName()),
                        new ServicePosition(registryIp, registryPort)));
        serviceChooser = new RandomServiceChooser();
    }

    @Override
    public <T> T getProxy(Class<T> clazz) {
        return getProxy(clazz.getName(), clazz);
    }

    @Override
    public <T> T getProxy(String name, Class<T> clazz) {
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
