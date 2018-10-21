package nx.funny.consumer;

/**
 * 获取待调用服务的代理对象，用于发送调用请求
 */
public interface ProxyFactory {
    <T> T getProxy(Class<T> clazz);
}
