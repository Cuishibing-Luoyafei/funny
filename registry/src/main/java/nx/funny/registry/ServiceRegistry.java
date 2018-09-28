package nx.funny.registry;

import java.util.List;

public interface ServiceRegistry {

    void register(ServiceInfo info);

    void remove(ServiceInfo info);

    List<ServiceInfo> retrive(Class<?> serviceClass);

}
