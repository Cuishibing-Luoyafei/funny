package nx.funny.provider;

import nx.funny.consumer.DefaultProxyFactory;
import nx.funny.provider.register.ServiceProviderRegister;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;

public class ServiceProviderRegisterTest {

    private ServiceProviderRegister register;

    @Before
    public void before(){
        ServicePositionProvider positionProvider = () -> new ServicePosition("localhost",9528);
        ServiceRegistry serviceRegistry = new DefaultProxyFactory("localhost",9527).getServiceRegistry();
        register = new ServiceProviderRegister(positionProvider,serviceRegistry);
    }

    @Test
    public void testRegister(){
        register.register(this.getClass(), typeName -> this);
    }
}
