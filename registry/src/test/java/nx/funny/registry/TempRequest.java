package nx.funny.registry;

import nx.funny.registry.client.RegistryClient;
import nx.funny.registry.client.RegistryNettyClient;
import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.server.RegistryNettyServer;
import nx.funny.registry.server.RegistryServer;
import org.junit.Before;
import org.junit.Test;

public class TempRequest {

    private RegistryServer registryServer;

    private RegistryClient registryClient;

    @Before
    public void before(){
        registryServer = new RegistryNettyServer(9527,null);
        registryClient = new RegistryNettyClient();
    }

    @Test
    public void startServer(){
        registryServer.start();
    }

    @Test
    public void request(){
        registryClient.init("127.0.0.1",9527);
        RegistryRequest request = new RegistryRequest();
        request.setOpertation(1);
        request.setPort(2344);
        request.setAddress(new short[]{123, 222, 102, 1});
        request.setTypeName(Integer.class.getName());
        registryClient.sendRequest(request);
    }

}
