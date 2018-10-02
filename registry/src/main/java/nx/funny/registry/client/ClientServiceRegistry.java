package nx.funny.registry.client;

import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;

import java.util.Set;

public class ClientServiceRegistry implements ServiceRegistry {

    private RegistryClient client;

    public ClientServiceRegistry(RegistryClient client) {
        this.client = client;
    }

    @Override
    public void register(ServiceInfo info) {

    }

    @Override
    public void remove(ServiceInfo info) {

    }

    @Override
    public void removeAll(ServiceType type) {

    }

    @Override
    public Set<ServicePosition> retrive(ServiceType type) {
        return null;
    }
}
