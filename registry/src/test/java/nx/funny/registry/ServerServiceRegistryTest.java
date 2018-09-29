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
        ServiceInfo serviceInfo1 = new ServiceInfo();
        serviceInfo1.setClazz(Integer.class);
        serviceInfo1.setAddress(InetSocketAddress.createUnresolved("127.0.0.1",9527));
        ServiceInfo serviceInfo2 = new ServiceInfo();
        serviceInfo2.setClazz(String.class);
        serviceInfo2.setAddress(InetSocketAddress.createUnresolved("127.0.0.1",9527));
        serviceRegistry.register(serviceInfo1);
        serviceRegistry.register(serviceInfo2);
        Set<ServiceInfo> result1 = serviceRegistry.retrive(Integer.class);
        Assert.assertNotNull(result1);
        Assert.assertTrue(result1.contains(serviceInfo1));
        Set<ServiceInfo> result2 = serviceRegistry.retrive(String.class);
        Assert.assertTrue(result2.contains(serviceInfo2));
    }
}
