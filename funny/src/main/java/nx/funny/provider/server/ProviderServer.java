package nx.funny.provider.server;

import nx.funny.provider.register.Register;
import nx.funny.transporter.server.NioServer;

/**
 * 服务提供者服务器端
 */
public class ProviderServer extends NioServer {
    private Register register;
    private ProviderRequestProcessor requestProcessor;
    public ProviderServer(Register register) {
        requestProcessor = new ProviderRequestProcessor(register);
        this.register = register;
    }

    public void start(){
        setRequestProcessor(requestProcessor);
        start(register.getPositionProvider().getServicePosition().getPort());
    }

}
