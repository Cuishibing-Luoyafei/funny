package nx.funny.consumer;

import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServiceRegistry;

/**
 * 获取待调用服务的代理对象，用于发送调用请求
 */
public interface ProxyFactory {
    <T> T getProxy(Class<T> clazz);

    <T> T getProxy(String name, Class<T> clazz);

    <T> T getProxy(Class<T> clazz, ServiceInfo info);

    ServiceRegistry getServiceRegistry();
}
