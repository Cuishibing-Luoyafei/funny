package nx.funny.sampleprovider.register;

import nx.funny.registry.ServiceType;

/**
 * 用来获取服务提供者对象
 */
public interface ServiceTargetFactory {
    Object getServiceTarget(ServiceType type);
}
