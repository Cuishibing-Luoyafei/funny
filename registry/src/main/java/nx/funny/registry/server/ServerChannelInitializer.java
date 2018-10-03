package nx.funny.registry.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LineBasedFrameDecoder;
import nx.funny.registry.server.decoder.RequestDecoder;
import nx.funny.registry.server.encoder.ResponseEncoder;
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
                    new LineBasedFrameDecoder(64 * 1024),
                    new RequestDecoder(),
                    new RegistryRequestHandler(),
                    new ResponseEncoder());
        }
    }
}
