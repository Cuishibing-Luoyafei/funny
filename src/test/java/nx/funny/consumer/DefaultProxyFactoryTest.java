package nx.funny.consumer;

import nx.funny.provider.register.ServiceProviderRegister;
import nx.funny.provider.server.ProviderRequestProcessor;
import nx.funny.registry.ServicePosition;
import nx.funny.transporter.server.NioServer;
import org.junit.Before;
import org.junit.Test;

public class DefaultProxyFactoryTest {

    private DefaultProxyFactory proxyFactory;

    @Before
    public void before() {
        proxyFactory = new DefaultProxyFactory("localhost", 9527);
    }

    // 注册服务
    @Test
    public void testRegisterService() {

        // 构建ServiceProviderRegister对象
        ServiceProviderRegister register = new ServiceProviderRegister(() -> new ServicePosition("localhost", 9528),
                proxyFactory.getServiceRegistry());
        register.register(RpcTestInterfaceImpl.class, typeName -> new RpcTestInterfaceImpl());

        // 启动服务
        NioServer server = new NioServer(new ProviderRequestProcessor(register));
        server.start(9528);
    }

    // 调用
    @Test
    public void testRpc() {
        RpcTestInterface rpcTest = proxyFactory.getProxy(RpcTestInterface.class);
        String hello = rpcTest.sayHello();
        System.out.println("sayHello:" + hello);
        System.out.println("sum(5,2):" + rpcTest.sum(5, 2));
    }

}
