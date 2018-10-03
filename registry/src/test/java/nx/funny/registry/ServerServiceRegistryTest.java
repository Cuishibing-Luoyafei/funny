package nx.funny.registry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.Set;

public class ServerServiceRegistryTest {

    private ServiceRegistry serviceRegistry;

    @Before
    public void before(){
        serviceRegistry = new ServerServiceRegistry();
    }

    @Test
    public void testRegister() {
        ServiceInfo serviceInfo1 = new ServiceInfo(Integer.class.getName(), InetSocketAddress.createUnresolved("127.0.1.1", 9527));
        ServiceInfo serviceInfo2 = new ServiceInfo(String.class.getName(), InetSocketAddress.createUnresolved("127.0.1.1", 9527));
        serviceRegistry.register(serviceInfo1);
        serviceRegistry.register(serviceInfo2);
        Set<ServicePosition> result1 = serviceRegistry.retrieve(new ServiceType(Integer.class.getName()));
        Assert.assertNotNull(result1);
        Assert.assertTrue(result1.contains(new ServicePosition(InetSocketAddress.createUnresolved("127.0.1.1",9527))));
        Set<ServicePosition> result2 = serviceRegistry.retrieve(new ServiceType(String.class.getName()));
        Assert.assertTrue(result2.contains(new ServicePosition(InetSocketAddress.createUnresolved("127.0.1.1",9527))));
    }

}
