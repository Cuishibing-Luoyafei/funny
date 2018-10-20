package nx.funny.registry;

import nx.funny.provider.ServicePositionProvider;
import nx.funny.provider.register.ServiceProviderRegister;
import nx.funny.provider.server.ProviderRequestProcessor;
import nx.funny.transporter.server.NioServer;

public class Registry {

    public static void main(String[] args) {
        ServicePositionProvider positionProvider = new ServicePositionProvider() {
            @Override
            public String getIp() {
                return "localhost";
            }

            @Override
            public int getPort() {
                return 9527;
            }
        };
        ServiceRegistry serviceRegistry = new ServerServiceHeapRegistry();
        ServiceProviderRegister register = new ServiceProviderRegister(positionProvider,
                serviceRegistry);
        register.register(ServiceRegistry.class, typeName -> serviceRegistry);
        NioServer server = new NioServer(new ProviderRequestProcessor(register));
        server.start(9527);
    }

}
