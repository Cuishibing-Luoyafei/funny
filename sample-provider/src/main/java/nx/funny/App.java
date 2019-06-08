package nx.funny;

import nx.funny.provider.register.FirstNewTargetFactory;
import nx.funny.provider.register.ServiceRegister;
import nx.funny.provider.server.ProviderServer;
import nx.funny.registry.HeapServiceRegistry;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String bindIp = "localhost";
        int port = 9530;

        // 根据注册中心地址和自身服务地址生成注册者对象
        ServiceRegister register = new ServiceRegister("localhost", 9527, HeapServiceRegistry.class,
                bindIp, port, false);

        // 直接注册服务对象
        // register.register(new SampleProviderInterfaceImpl());

        // 使用包扫描的方式注册
        register.scan(FirstNewTargetFactory.INSTANCE(), "nx.funny");

        ProviderServer server = new ProviderServer(register);

        // 启动服务提供者服务
        server.start();
    }
}
