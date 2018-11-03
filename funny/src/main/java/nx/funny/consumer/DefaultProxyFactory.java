package nx.funny.consumer;

import lombok.Getter;
import lombok.Setter;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServiceRegistry;

import java.lang.reflect.Proxy;

public class DefaultProxyFactory implements ProxyFactory {

    @Getter
    private ServiceRegistry serviceRegistry;

    @Setter
    private ServiceChooser serviceChooser;

    public DefaultProxyFactory(String registryIp, int registryPort) {
        String registryServiceName = ServiceRegistry.class.getName();
        serviceRegistry = getProxy(ServiceRegistry.class,
                new ServiceInfo(registryServiceName, registryIp, registryPort));
        serviceChooser = new RandomServiceChooser();
    }

    @Override
    public <T> T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz}, new DefaultInvocationHandler(clazz.getName(),
                        serviceRegistry, serviceChooser));
        return (T) o;
    }

    @Override
    public <T> T getProxy(String name, Class<T> clazz) {
        Object o = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz}, new DefaultInvocationHandler(name, serviceRegistry, serviceChooser));
        return (T) o;
    }

    @Override
    public <T> T getProxy(Class<T> clazz, ServiceInfo info) {
        Object o = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz}, new SpecificInvocationHandler(info));
        return (T) o;
    }

}
