package nx.funny.consumer;

import nx.funny.provider.register.FirstNewTargetFactory;
import nx.funny.provider.register.ServiceRegister;
import nx.funny.provider.server.ProviderServer;
import nx.funny.registry.HeapServiceRegistry;
import org.junit.Before;
import org.junit.Test;

public class DefaultProxyFactoryTest {

    private DefaultProxyFactory proxyFactory;

    @Before
    public void before() {
        proxyFactory = new DefaultProxyFactory("localhost", 9527, HeapServiceRegistry.class);
    }

    // 注册服务
    @Test
    public void testRegisterService() {

        // 构建ServiceProviderRegister对象
        ServiceRegister serviceRegister = new ServiceRegister("localhost", 9527, HeapServiceRegistry.class,
                "localhost", 9528, false);
        serviceRegister.register(RpcTestInterfaceImpl.class, FirstNewTargetFactory.INSTANCE());

        //serviceRegister.serviceRegister("RemoteObject",new RpcTestInterfaceImpl("instance 2"));

        // 启动服务
        ProviderServer server = new ProviderServer(serviceRegister);
        server.start();
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
