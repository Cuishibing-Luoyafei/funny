package nx.funny.consumer;

import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class DefaultProxyFactoryTest {

    private DefaultProxyFactory proxyFactory;

    @Before
    public void before() {
        proxyFactory = new DefaultProxyFactory();
    }

    @Test
    public void testGetProxy() {
        ServiceRegistry proxy = proxyFactory.getProxy(ServiceRegistry.class, new ServicePosition("localhost", 9527));
        Set<ServiceInfo> serviceInfos = proxy.retrieve(ServiceRegistry.class.getName());
        System.out.println(serviceInfos.toString());
        proxy.remove(new ServiceInfo(ServiceRegistry.class.getName(), "localhost", 9527));

        serviceInfos = proxy.retrieve(ServiceRegistry.class.getName());
        System.out.println(serviceInfos.toString());
    }

}
