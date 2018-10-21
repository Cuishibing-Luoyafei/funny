package nx.funny.provider.register;

/**
 * 用来获取服务提供者对象
 */
public interface ServiceTargetFactory {
    Object getServiceTarget(String typeName);
}
