package nx.funny.registry;

import java.util.Set;

public interface ServiceRegistry {

    void register(ServiceInfo info);

    void remove(ServiceInfo info);

    void removeAll(ServiceType type);

    Set<ServicePosition> retrive(ServiceType type);

}
