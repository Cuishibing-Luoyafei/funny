package nx.funny.registry.server.transport;

import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;

public interface RequestProcessor {
    RegistryResponse processRequest(RegistryRequest request);
}
