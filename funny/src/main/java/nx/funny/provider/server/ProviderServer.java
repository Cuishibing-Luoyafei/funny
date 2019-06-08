package nx.funny.provider.server;

import nx.funny.provider.register.ServiceRegister;
import nx.funny.transporter.server.NioServer;

/**
 * 服务提供者服务器端
 */
public class ProviderServer extends NioServer {
    private ServiceRegister serviceRegister;
    private ProviderRequestProcessor requestProcessor;

    public ProviderServer(ServiceRegister serviceRegister) {
        super(serviceRegister.getPositionProvider().getServicePosition().getIp(),
                serviceRegister.getPositionProvider().getServicePosition().getPort());
        requestProcessor = new ProviderRequestProcessor(serviceRegister);
        this.serviceRegister = serviceRegister;
    }

    public ProviderServer(String bindIp, int port, ServiceRegister serviceRegister) {
        super(bindIp, port);
        requestProcessor = new ProviderRequestProcessor(serviceRegister);
        this.serviceRegister = serviceRegister;
    }

    @Override
    public void start() {
        serviceRegister.syncData();
        setRequestProcessor(requestProcessor);
        super.start();
    }

}
