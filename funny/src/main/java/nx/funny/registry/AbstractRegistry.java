package nx.funny.registry;

import nx.funny.provider.ServicePositionProvider;
import nx.funny.provider.register.ServiceRegister;
import nx.funny.provider.server.ProviderServer;
import nx.funny.transporter.server.AbstractServer;
import nx.funny.transporter.server.Server;

public abstract class AbstractRegistry extends AbstractServer implements ServiceRegistry {
    protected Server server;

    public AbstractRegistry(String bindIp, int port) {
        super(bindIp, port);
    }

    @Override
    public void start() {
        ServicePositionProvider positionProvider = () -> new ServicePosition(bindIp, port);
        ServiceRegister serviceRegister = new ServiceRegister(positionProvider, this, true);
        // 注册自己
        serviceRegister.register(ServiceRegistry.class, this);
        server = new ProviderServer(bindIp, port, serviceRegister);
        server.start();
    }

    @Override
    public void destroy() {
        server.destroy();
    }

}