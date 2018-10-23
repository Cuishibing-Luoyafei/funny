package nx.funny.provider.server;

import nx.funny.provider.register.Register;
import nx.funny.transporter.server.NioServer;

/**
 * 服务提供者服务器端
 */
public class ProviderServer extends NioServer {

    public ProviderServer(Register register) {
        ProviderRequestProcessor requestProcessor = new ProviderRequestProcessor(register);
        setRequestProcessor(requestProcessor);
    }
}
