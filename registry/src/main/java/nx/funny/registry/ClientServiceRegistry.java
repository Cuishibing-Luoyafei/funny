package nx.funny.registry.client;

import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;
import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;

import java.util.Set;

public class ClientServiceRegistry implements ServiceRegistry {

    private RegistryClient client;

    public ClientServiceRegistry(RegistryClient client) {
        this.client = client;
    }

    private RegistryRequest generateRequest(ServiceInfo info, int operation) {
        RegistryRequest request = new RegistryRequest();
        request.setOperation(operation);
        request.setType(info.getType());
        request.setPosition(info.getPosition());
        return request;
    }

    private void processResponse(RegistryResponse response) {
        if (response.getCode() != RegistryResponse.CODE_SUCCESS) {
            String msg = response.getMsg();
            throw new RuntimeException("Registry exception:" + (msg == null ? "" : msg));
        }
    }

    @Override
    public void register(ServiceInfo info) {
        RegistryRequest request = generateRequest(info, RegistryRequest.OPERATION_REGISTER);
        RegistryResponse response = client.sendRequest(request);
        processResponse(response);
    }

    @Override
    public void remove(ServiceInfo info) {
        RegistryRequest request = generateRequest(info, RegistryRequest.OPERATION_REMOVE);
        RegistryResponse response = client.sendRequest(request);
        processResponse(response);
    }

    @Override
    public void removeAll(ServiceType type) {
        RegistryRequest request = generateRequest(new ServiceInfo(type, null), RegistryRequest.OPERATION_REMOVE_ALL);
        RegistryResponse response = client.sendRequest(request);
        processResponse(response);
    }

    @Override
    public Set<ServiceInfo> retrieve(String name) {
        ServiceType type = new ServiceType();
        type.setName(name);
        RegistryRequest request = generateRequest(new ServiceInfo(type, null), RegistryRequest.OPERATION_RETRIEVE);
        RegistryResponse response = client.sendRequest(request);
        processResponse(response);
        return response.getInfos();
    }
}
