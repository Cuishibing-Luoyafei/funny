package nx.funny.registry.server.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.server.RegistryServer;
import nx.funny.registry.server.ServerServiceHeapRegistry;

/**
 * 服务注册中心服务器的netty实现
 * */
public class RegistryNettyServer implements RegistryServer {
    public static final int DEFAULT_PORT = 9527;
    private int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private ServiceRegistry serviceRegistry;

    private RequestProcessor requestProcessor;

    public RegistryNettyServer(int port, ServiceRegistry serviceRegistry) {
        this.port = port;
        this.serviceRegistry = serviceRegistry;
        this.requestProcessor = new DefaultRequestProcessor(this.serviceRegistry);
    }

    public RegistryNettyServer(int port) {
        this(port, new ServerServiceHeapRegistry());// 默认是基于内存的注册中心
    }

    public RegistryNettyServer() {
        this(DEFAULT_PORT);
    }

    @Override
    public void start() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer(requestProcessor))
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
