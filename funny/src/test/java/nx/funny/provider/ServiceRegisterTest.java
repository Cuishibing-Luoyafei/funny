package nx.funny.provider;

import nx.funny.consumer.factory.DefaultProxyFactory;
import nx.funny.provider.register.ServiceRegister;
import nx.funny.registry.HeapServiceRegistry;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;

public class ServiceRegisterTest {

    private ServiceRegister serviceRegister;

    @Before
    public void before(){
        ServicePositionProvider positionProvider = () -> new ServicePosition("localhost",9528);
        ServiceRegistry serviceRegistry = new DefaultProxyFactory("localhost", 9527, HeapServiceRegistry.class).getServiceRegistry();
        serviceRegister = new ServiceRegister(positionProvider, serviceRegistry, false);
    }

    @Test
    public void testRegister(){
        serviceRegister.register(this.getClass(), typeName -> this);

    }
}
