package nx.funny.transporter.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.Setter;
import nx.funny.transporter.request.InvokerRequest;
import nx.funny.transporter.request.JdkMessageTranslator;
import nx.funny.transporter.response.InvokerResponse;
import nx.funny.transporter.server.channlehandler.NettyInvokerRequestDecoder;
import nx.funny.transporter.server.channlehandler.NettyInvokerRequestHandler;
import nx.funny.transporter.server.channlehandler.NettyInvokerResponseEncoder;


public class NioServer implements Server {
    @Setter
    @Getter
    private InvokerRequestProcessor requestProcessor;

    private JdkMessageTranslator<InvokerRequest> jdkMessageRequestTranslator;
    private JdkMessageTranslator<InvokerResponse> jdkMessageResponseTranslator;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NioServer() {
        Object translator = new JdkMessageTranslator<>();
        jdkMessageRequestTranslator = (JdkMessageTranslator<InvokerRequest>) translator;
        jdkMessageResponseTranslator = (JdkMessageTranslator<InvokerResponse>) translator;
    }

    public NioServer(InvokerRequestProcessor requestProcessor) {
        this();
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
                                    new NettyInvokerRequestDecoder(jdkMessageRequestTranslator),
                                    new NettyInvokerRequestHandler(requestProcessor),
                                    new NettyInvokerResponseEncoder(jdkMessageResponseTranslator));
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
