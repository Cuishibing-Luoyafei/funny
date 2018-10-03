package nx.funny.registry;

import com.google.gson.Gson;
import nx.funny.registry.client.RegistryClient;
import nx.funny.registry.client.RegistryNettyClient;
import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;
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
        request.setOperation(RegistryRequest.OPERATION_RETRIEVE);
        request.setType(new ServiceType(Integer.class.getName()));
        request.setPosition(new ServicePosition("21.23.34.13", 9090));
        RegistryResponse response = registryClient.sendRequest(request);
        System.out.println(new Gson().toJson(response));
    }

}
