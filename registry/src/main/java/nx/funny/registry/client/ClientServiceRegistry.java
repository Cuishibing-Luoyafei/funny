package nx.funny.registry.client;

import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;
import nx.funny.registry.request.RegistryRequest;

import java.util.Set;

public class ClientServiceRegistry implements ServiceRegistry {

    private RegistryClient client;

    public ClientServiceRegistry(RegistryClient client) {
        this.client = client;
    }

    private RegistryRequest generateRequest(ServiceInfo info, int operation) {
        RegistryRequest request = new RegistryRequest();
        request.setOperation(operation);
        request.setTypeName(info.getType().getType().getName());
        return request;
    }

    @Override
    public void register(ServiceInfo info) {
        RegistryRequest request = generateRequest(info, RegistryRequest.OPERATION_REGISTER);
        client.sendRequest(request);
    }

    @Override
    public void remove(ServiceInfo info) {
        RegistryRequest request = generateRequest(info, RegistryRequest.OPERATION_REGISTER);
        client.sendRequest(request);
    }

    @Override
    public void removeAll(ServiceType type) {

    }

    @Override
    public Set<ServicePosition> retrieve(ServiceType type) {
        return null;
    }
}
