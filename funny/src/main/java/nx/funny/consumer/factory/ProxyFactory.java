package nx.funny.consumer.factory;

import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServiceRegistry;

/**
 * 获取待调用服务的代理对象，用于发送调用请求
 */
public interface ProxyFactory {

    /**
     * 生成指定接口类型的代理对象,对接口中方法的调用会转发到在注册中心注册的远程对象中去;
     * 使用此方法生成的代理对象之前必须要设置注册中心
     *
     * @param clazz 接口类型
     * @return 生成的代理对象
     */
    <T> T getProxy(Class<T> clazz);

    /**
     * 生成指定接口类型的代理对象,对接口中方法的调用会转发到由info指定的远程对象中去;
     *
     * @param clazz 接口类型
     * @return 生成的代理对象
     */
    <T> T getProxy(Class<T> clazz, ServiceInfo info);

    /**
     * 获取代理工厂中的注册中心对象
     *
     * @return 注册中心对象, 如果不存在返回null
     */
    ServiceRegistry getServiceRegistry();

}
