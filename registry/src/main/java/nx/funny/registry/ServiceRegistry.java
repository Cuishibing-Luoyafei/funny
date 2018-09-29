package nx.funny.registry;

import java.util.Set;

public interface ServiceRegistry {

    void register(ServiceInfo info);

    void remove(ServiceInfo info);

    Set<ServiceInfo> retrive(Class<?> serviceClass);

}
