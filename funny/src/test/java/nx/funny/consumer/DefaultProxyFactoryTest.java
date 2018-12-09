package nx.funny.consumer;

import nx.funny.provider.register.FirstNewTargetFactory;
import nx.funny.provider.register.Register;
import nx.funny.provider.server.ProviderServer;
import nx.funny.registry.ServerServiceHeapRegistry;
import org.junit.Before;
import org.junit.Test;

public class DefaultProxyFactoryTest {

    private DefaultProxyFactory proxyFactory;

    @Before
    public void before() {
        proxyFactory = new DefaultProxyFactory("localhost", 9527, ServerServiceHeapRegistry.class);
    }

    // 注册服务
    @Test
    public void testRegisterService() {

        // 构建ServiceProviderRegister对象
        Register register = new Register("localhost", 9527, ServerServiceHeapRegistry.class,
                "localhost", 9528, false);
        register.register(RpcTestInterfaceImpl.class, FirstNewTargetFactory.INSTANCE());

        //register.register("RemoteObject",new RpcTestInterfaceImpl("instance 2"));

        // 启动服务
        ProviderServer server = new ProviderServer(register);
        server.start(9528);
    }

    // 调用
    @Test
    public void testRpc() {
        RpcTestInterface rpcTest = proxyFactory.getProxy(RpcTestInterface.class);
        for(int i=0;i<100;i++){
            String hello = rpcTest.sayHello();
            System.out.println("sayHello:" + hello);
            System.out.println("sum(5,2):" + rpcTest.sum(5, 2));
        }

//        RpcTestInterface remoteObject = proxyFactory.getProxy("RemoteObject", RpcTestInterface.class);
//
//        System.out.println("sayHello:" + remoteObject.sayHello());
//        System.out.println("sum(5,2):" + remoteObject.sum(5, 2));
    }

}
