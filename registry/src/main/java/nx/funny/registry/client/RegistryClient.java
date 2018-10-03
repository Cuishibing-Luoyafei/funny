package nx.funny.registry.client;

import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;

public interface RegistryClient {

    void init(String serverAddress,int port);

    RegistryResponse sendRequest(RegistryRequest request);

}
