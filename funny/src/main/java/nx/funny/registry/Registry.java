package nx.funny.registry;

import nx.funny.provider.ServicePositionProvider;
import nx.funny.provider.register.Register;
import nx.funny.provider.server.ProviderRequestProcessor;
import nx.funny.transporter.server.NioServer;

public class Registry {

    public static void init(String ip,int port,ServiceRegistry serviceRegistry){
        ServicePositionProvider positionProvider = () -> new ServicePosition(ip,port);
        Register register = new Register(positionProvider,
                serviceRegistry);
        // 注册自己
        register.register(ServiceRegistry.class, typeName -> serviceRegistry);
        NioServer server = new NioServer(new ProviderRequestProcessor(register));
        server.start(9527);
    }

    public static void main(String[] args) {
        ServiceRegistry serviceRegistry = new ServerServiceHeapRegistry();
        init("localhost",9527,serviceRegistry);
    }

}
