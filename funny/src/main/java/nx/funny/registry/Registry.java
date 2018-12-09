package nx.funny.registry;

import nx.funny.provider.ServicePositionProvider;
import nx.funny.provider.register.Register;
import nx.funny.provider.server.ProviderServer;

public class Registry {

    public static void init(String ip, int port, ServiceRegistry serviceRegistry) {
        ServicePositionProvider positionProvider = () -> new ServicePosition(ip, port);
        Register register = new Register(positionProvider, serviceRegistry, true);
        // 注册自己
        //register.register(ServiceRegistry.SERVICE_REGISTRY_NAME, ServiceRegistry.SERVICE_REGISTRY_NAME, serviceRegistry);
        register.register(ServiceRegistry.class, serviceRegistry);
        ProviderServer server = new ProviderServer(register);
        server.start();
    }

}
