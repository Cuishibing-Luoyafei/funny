package nx.funny.registry.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import nx.funny.registry.server.decoder.RequestDecoder;
import nx.funny.registry.server.handler.RegistryRequestHandler;

public class ServerChannelInitializer extends ChannelInitializer {

    private boolean inbound;

    public ServerChannelInitializer(boolean inbound) {
        this.inbound = inbound;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        if(inbound){
            channel.pipeline().addLast(
                    new RequestDecoder(),
                    new RegistryRequestHandler());
        }
    }
}