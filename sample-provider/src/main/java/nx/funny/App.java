package nx.funny;

import nx.funny.provider.register.Register;
import nx.funny.provider.server.ProviderServer;
import nx.funny.registry.ServerServiceHeapRegistry;
import nx.funny.sampleprovider.SampleProviderInterfaceImpl;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // 根据注册中心地址和自身服务地址生成注册者对象
        Register register = new Register("localhost", 9527, ServerServiceHeapRegistry.class,
                "localhost", 9528, false);

        // 直接注册服务对象
        register.register(new SampleProviderInterfaceImpl());

        // 使用包扫描的方式注册
        // register.scan(FirstNewTargetFactory.INSTANCE(),"nx.funny");

        ProviderServer server = new ProviderServer(register);

        // 启动服务提供者服务
        server.start();
    }
}
