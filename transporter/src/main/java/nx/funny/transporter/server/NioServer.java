package nx.funny.transporter.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.Setter;
import nx.funny.transporter.request.StringMessageInvokerRequestDecoder;
import nx.funny.transporter.response.InvokerResponseStringMessageEncoder;
import nx.funny.transporter.server.channlehandler.NettyInvokerRequestDecoder;
import nx.funny.transporter.server.channlehandler.NettyInvokerRequestHandler;
import nx.funny.transporter.server.channlehandler.NettyInvokerResponseEncoder;


public class NioServer implements Server {
    @Setter
    @Getter
    private InvokerRequestProcessor requestProcessor;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NioServer() {
    }

    public NioServer(InvokerRequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @Override
    public void start(int port) {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new NettyInvokerRequestDecoder(new StringMessageInvokerRequestDecoder()),
                                    new NettyInvokerRequestHandler(requestProcessor),
                                    new NettyInvokerResponseEncoder(new InvokerResponseStringMessageEncoder()));
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = bootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void destroy() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
