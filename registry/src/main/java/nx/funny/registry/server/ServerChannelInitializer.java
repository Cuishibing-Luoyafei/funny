package nx.funny.registry.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LineBasedFrameDecoder;
import nx.funny.registry.server.decoder.RequestDecoder;
import nx.funny.registry.server.encoder.ResponseEncoder;
import nx.funny.registry.server.handler.RegistryRequestHandler;

public class ServerChannelInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                new LineBasedFrameDecoder(64 * 1024),
                new RequestDecoder(),
                new RegistryRequestHandler(),
                new ResponseEncoder());
    }
}
