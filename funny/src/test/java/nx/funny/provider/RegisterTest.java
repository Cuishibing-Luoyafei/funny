package nx.funny.provider;

import nx.funny.consumer.DefaultProxyFactory;
import nx.funny.provider.register.Register;
import nx.funny.registry.ServerServiceHeapRegistry;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;

public class RegisterTest {

    private Register register;

    @Before
    public void before(){
        ServicePositionProvider positionProvider = () -> new ServicePosition("localhost",9528);
        ServiceRegistry serviceRegistry = new DefaultProxyFactory("localhost", 9527, ServerServiceHeapRegistry.class).getServiceRegistry();
        register = new Register(positionProvider, serviceRegistry, false);
    }

    @Test
    public void testRegister(){
        register.register(this.getClass(), typeName -> this);

    }
}
