package nx.funny.registry.server;

import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;

public interface RequestProcessor {
    RegistryResponse processRequest(RegistryRequest request);
}
