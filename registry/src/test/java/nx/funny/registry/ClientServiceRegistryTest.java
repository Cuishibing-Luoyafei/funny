package nx.funny.registry;

import com.google.gson.Gson;
import nx.funny.registry.client.ClientServiceRegistry;
import nx.funny.registry.client.RegistryClient;
import nx.funny.registry.client.transport.RegistryNettyClient;
import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;
import nx.funny.registry.server.RegistryServer;
import nx.funny.registry.server.transport.RegistryNettyServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class ClientServiceRegistryTest {

    private RegistryServer registryServer;

    private RegistryClient registryClient;

    @Before
    public void before() {
        registryServer = new RegistryNettyServer(9527);
        registryClient = new RegistryNettyClient();
    }

    @Test
    public void startServer() {
        registryServer.start();
    }

    @Test
    public void request() {
        RegistryRequest request = new RegistryRequest();
        request.setOperation(RegistryRequest.OPERATION_RETRIEVE);
        request.setType(new ServiceType(Integer.class.getName()));
        request.setPosition(new ServicePosition("21.23.34.13", 9090));
        RegistryResponse response = registryClient.sendRequest(request);
        System.out.println(new Gson().toJson(response));
    }

    @Test
    public void testClientServiceRegistry() {
        registryClient.init("localhost", 9527);
        ServiceRegistry serviceRegistry = new ClientServiceRegistry(registryClient);

        serviceRegistry.register(new ServiceInfo(Integer.class.getName(), "127.0.0.1", 9999));
        Set<ServiceInfo> infos = serviceRegistry.retrieve(Integer.class.getName());
        Assert.assertTrue(infos.contains(new ServiceInfo(Integer.class.getName(), "127.0.0.1", 9999)));

        serviceRegistry.register(new ServiceInfo(Integer.class.getName(), "127.0.0.2", 9999));
        infos = serviceRegistry.retrieve(Integer.class.getName());
        Assert.assertTrue(infos.contains(new ServiceInfo(Integer.class.getName(),"127.0.0.1", 9999)));
        Assert.assertTrue(infos.contains(new ServiceInfo(Integer.class.getName(),"127.0.0.2", 9999)));

        serviceRegistry.remove(new ServiceInfo(Integer.class.getName(), "127.0.0.2", 9999));
        infos = serviceRegistry.retrieve(Integer.class.getName());
        Assert.assertTrue(infos.contains(new ServiceInfo(Integer.class.getName(),"127.0.0.1", 9999)));
        Assert.assertTrue(!infos.contains(new ServiceInfo(Integer.class.getName(),"127.0.0.2", 9999)));

        serviceRegistry.removeAll(new ServiceType(Integer.class.getName()));
        infos = serviceRegistry.retrieve(Integer.class.getName());
        Assert.assertTrue(infos != null && infos.size() == 0);
    }
}
