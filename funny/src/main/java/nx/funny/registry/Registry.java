package nx.funny.registry;

import nx.funny.sampleprovider.ServicePositionProvider;
import nx.funny.sampleprovider.register.Register;
import nx.funny.sampleprovider.server.ProviderRequestProcessor;
import nx.funny.sampleprovider.server.ProviderServer;
import nx.funny.transporter.server.NioServer;

public class Registry {

    public static void init(String ip,int port,ServiceRegistry serviceRegistry){
        ServicePositionProvider positionProvider = () -> new ServicePosition(ip,port);
        Register register = new Register(positionProvider,
                serviceRegistry);
        // 注册自己
        register.register(ServiceRegistry.class, serviceRegistry);
        ProviderServer server = new ProviderServer(register);
        server.start();
    }

    public static void main(String[] args) {
        ServiceRegistry serviceRegistry = new ServerServiceHeapRegistry();
        init("localhost",9527,serviceRegistry);
    }

}
