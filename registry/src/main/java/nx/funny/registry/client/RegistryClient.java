package nx.funny.registry.client;

import nx.funny.registry.request.RegistryRequest;

public interface RegistryClient {

    void init(String serverAddress,int port);

    void sendRequest(RegistryRequest request);

}
