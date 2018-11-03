package nx.funny.registry;

import java.util.List;
import java.util.Set;

public interface ServiceRegistry {

    String SERVICE_REGISTRY_NAME = ServiceRegistry.class.getName();

    void register(ServiceInfo info);

    void register(List<ServiceInfo> services);

    void remove(ServiceInfo info);

    void removeAll(ServiceType type);

    Set<ServiceInfo> retrieve(String name);

}
