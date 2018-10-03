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
import nx.funny.registry.client.handler.SendRequestHandler;
import nx.funny.registry.request.RegistryRequest;
import nx.funny.registry.response.RegistryResponse;

public class RegistryNettyClient implements RegistryClient {
    private String serverAddress;
    private int port;
    @Override
    public void init(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    @Override
    public RegistryResponse sendRequest(RegistryRequest request) {
        RegistryResponse[] responseResult = new RegistryResponse[1];
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new SendRequestHandler(request),
                                    new RequestEncoder(),
                                    new LineBasedFrameDecoder(64 * 1024),
                                    new ResponseDecoder(responseResult));
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = bootstrap.connect(serverAddress, port).sync();
            f.channel().closeFuture().sync();
            synchronized (responseResult) {
                return responseResult[0];
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
