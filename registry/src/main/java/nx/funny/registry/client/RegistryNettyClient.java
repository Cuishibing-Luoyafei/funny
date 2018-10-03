package nx.funny.registry.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import nx.funny.registry.client.decoder.ResponseDecoder;
import nx.funny.registry.client.encoder.RequestEncoder;
import nx.funny.registry.client.handler.RegistryResponseHandler;
import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;

public class RegistryNettyClient implements RegistryClient {
    private String serverAddress;
    private int port;

    private final RegistryResponse[] responseContainer = new RegistryResponse[1];

    private EventLoopGroup workerGroup;
    private ChannelFuture connectFuture;

    @Override
    public void init(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
        workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                new RequestEncoder(),
                                new LineBasedFrameDecoder(64 * 1024),
                                new ResponseDecoder(),
                                new RegistryResponseHandler(responseContainer));
                    }
                })
                .option(ChannelOption.SO_KEEPALIVE, true);
        connectFuture = bootstrap.connect(serverAddress, port);
        try {
            connectFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public RegistryResponse sendRequest(RegistryRequest request) {
        ChannelFuture channelFuture = connectFuture.channel().pipeline().writeAndFlush(request);
        try {
            channelFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        RegistryResponse response = null;
        while (true) {
            synchronized (responseContainer) {
                if (responseContainer[0] == null) {
                    try {
                        responseContainer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                } else {
                    response = responseContainer[0];
                    responseContainer[0] = null;
                    break;
                }
            }
        }
        return response;
    }

    @Override
    public void shutdown() {
        try {
            workerGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
