package nx.funny.sampleprovider;

import nx.funny.sampleconsumer.DefaultProxyFactory;
import nx.funny.sampleprovider.register.Register;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;

public class RegisterTest {

    private Register register;

    @Before
    public void before(){
        ServicePositionProvider positionProvider = () -> new ServicePosition("localhost",9528);
        ServiceRegistry serviceRegistry = new DefaultProxyFactory("localhost",9527).getServiceRegistry();
        register = new Register(positionProvider,serviceRegistry);
    }

    @Test
    public void testRegister(){
        register.register(this.getClass(), typeName -> this);
    }
}
