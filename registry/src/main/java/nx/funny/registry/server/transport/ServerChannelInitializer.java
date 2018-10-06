package nx.funny.registry.server.transport;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LineBasedFrameDecoder;
import nx.funny.registry.server.transport.decoder.RequestDecoder;
import nx.funny.registry.server.transport.encoder.ResponseEncoder;
import nx.funny.registry.server.transport.handler.RegistryRequestHandler;

public class ServerChannelInitializer extends ChannelInitializer {

    private RequestProcessor requestProcessor;

    public ServerChannelInitializer(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                new LineBasedFrameDecoder(64 * 1024),
                new RequestDecoder(),
                new RegistryRequestHandler(requestProcessor),
                new ResponseEncoder());
    }
}
