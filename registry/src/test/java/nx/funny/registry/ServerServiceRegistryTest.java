package nx.funny.registry;

import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.request.RegistryRequestHead;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
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
        ServiceInfo serviceInfo1 = new ServiceInfo(Integer.class,InetSocketAddress.createUnresolved("127.0.1.1",9527));
        ServiceInfo serviceInfo2 = new ServiceInfo(String.class,InetSocketAddress.createUnresolved("127.0.1.1",9527));
        serviceRegistry.register(serviceInfo1);
        serviceRegistry.register(serviceInfo2);
        Set<ServicePosition> result1 = serviceRegistry.retrive(new ServiceType(Integer.class));
        Assert.assertNotNull(result1);
        Assert.assertTrue(result1.contains(new ServicePosition(InetSocketAddress.createUnresolved("127.0.1.1",9527))));
        Set<ServicePosition> result2 = serviceRegistry.retrive(new ServiceType(String.class));
        Assert.assertTrue(result2.contains(new ServicePosition(InetSocketAddress.createUnresolved("127.0.1.1",9527))));
    }

}
