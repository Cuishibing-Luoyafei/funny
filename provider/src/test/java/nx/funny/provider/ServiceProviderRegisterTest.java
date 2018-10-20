package nx.funny.provider;

import nx.funny.provider.register.ServiceProviderRegister;
import nx.funny.registry.ClientServiceRegistry;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.client.RegistryClient;
import nx.funny.registry.client.transport.RegistryOioClient;
import org.junit.Before;
import org.junit.Test;

public class ServiceProviderRegisterTest {

    private ServiceProviderRegister register;

    @Before
    public void before(){
        ServicePositionProvider positionProvider = new ServicePositionProvider() {
            @Override
            public String getIp() {
                return "127.0.0.1";
            }

            @Override
            public int getPort() {
                return 99999;
            }
        };
        RegistryClient registryClient = new RegistryOioClient();
        registryClient.init("localhost",9527);
        ServiceRegistry serviceRegistry = new ClientServiceRegistry(registryClient);
        register = new ServiceProviderRegister(positionProvider,serviceRegistry);
    }

    @Test
    public void testRegister(){
        register.register(this.getClass());
    }
}
