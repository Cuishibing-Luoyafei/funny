package nx.funny.consumer;

import nx.funny.registry.ServiceInfo;

import java.util.Set;

/**
 * 负载均衡器，用来选择具体的服务进行调用
 * */
public interface ServiceChooser {

    ServiceInfo choose(Set<ServiceInfo> candidates);

}
