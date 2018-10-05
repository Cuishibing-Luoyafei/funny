package nx.funny.provider.service;

import nx.funny.registry.ServiceInfo;

/**
 * @author luoyafei
 * @date 2018-10-05
 */
public interface IServiceProvider {

    /**
     * 注册
     *
     * @return
     */
    boolean register();

    /**
     * 心跳检测
     *
     * @return
     */
    boolean ping();

}