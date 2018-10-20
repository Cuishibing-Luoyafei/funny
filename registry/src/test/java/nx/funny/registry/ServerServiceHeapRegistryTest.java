package nx.funny.registry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class ServerServiceHeapRegistryTest {

    private ServiceRegistry serviceRegistry;

    @Before
    public void before(){
        serviceRegistry = new ServerServiceHeapRegistry();
    }

    @Test
    public void testRegister() {
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
