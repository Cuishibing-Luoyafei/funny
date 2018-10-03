package nx.funny.registry;

import nx.funny.registry.server.ServerServiceRegistry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class ServerServiceRegistryTest {

    private ServiceRegistry serviceRegistry;

    @Before
    public void before(){
        serviceRegistry = ServerServiceRegistry.INSTANCE;
    }

    @Test
    public void testRegister() {
        serviceRegistry.register(new ServiceInfo(Integer.class.getName(), "127.0.0.1", 9999));
        Set<ServicePosition> positions = serviceRegistry.retrieve(new ServiceType(Integer.class.getName()));
        Assert.assertTrue(positions.contains(new ServicePosition("127.0.0.1", 9999)));

        serviceRegistry.register(new ServiceInfo(Integer.class.getName(), "127.0.0.2", 9999));
        positions = serviceRegistry.retrieve(new ServiceType(Integer.class.getName()));
        Assert.assertTrue(positions.contains(new ServicePosition("127.0.0.1", 9999)));
        Assert.assertTrue(positions.contains(new ServicePosition("127.0.0.2", 9999)));

        serviceRegistry.remove(new ServiceInfo(Integer.class.getName(), "127.0.0.2", 9999));
        positions = serviceRegistry.retrieve(new ServiceType(Integer.class.getName()));
        Assert.assertTrue(positions.contains(new ServicePosition("127.0.0.1", 9999)));
        Assert.assertTrue(!positions.contains(new ServicePosition("127.0.0.2", 9999)));

        serviceRegistry.removeAll(new ServiceType(Integer.class.getName()));
        positions = serviceRegistry.retrieve(new ServiceType(Integer.class.getName()));
        Assert.assertTrue(positions == null);
    }

}
