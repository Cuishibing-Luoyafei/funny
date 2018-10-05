package nx.funny.registry.server.transport;

import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;
import nx.funny.registry.server.ServerServiceRegistry;

import java.util.Set;

import static nx.funny.registry.request.RegistryRequest.*;
import static nx.funny.registry.response.RegistryResponse.CODE_SUCCESS;

public class DefaultRequestProcessor implements RequestProcessor {

    private ServiceRegistry registry = ServerServiceRegistry.INSTANCE;

    public DefaultRequestProcessor() {
    }

    public DefaultRequestProcessor(ServiceRegistry registry) {
        this.registry = registry;
    }

    @Override
    public RegistryResponse processRequest(RegistryRequest request) {
        RegistryResponse response = new RegistryResponse();
        response.setCode(CODE_SUCCESS);
        ServiceInfo serviceInfo = new ServiceInfo(request.getType(),
                request.getPosition());
        try {
            switch (request.getOperation()) {
                case OPERATION_REGISTER:
                    registry.register(serviceInfo);
                    break;
                case OPERATION_REMOVE:
                    registry.remove(serviceInfo);
                    break;
                case OPERATION_REMOVE_ALL:
                    registry.removeAll(serviceInfo.getType());
                    break;
                case OPERATION_RETRIEVE:
                    Set<ServiceInfo> positions = registry.retrieve(serviceInfo.getType().getName());
                    response.setInfos(positions);
                    break;
            }
        } catch (Throwable e) {
            response.setCode(RegistryResponse.CODE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    public ServiceRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(ServiceRegistry registry) {
        this.registry = registry;
    }
}