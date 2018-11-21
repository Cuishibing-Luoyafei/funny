package nx.funny;

import nx.funny.provider.register.Register;
import nx.funny.provider.server.ProviderServer;
import nx.funny.registry.ServerServiceHeapRegistry;
import nx.funny.sampleprovider.SampleProviderInterfaceImpl;
import nx.funny.sampleprovider.SampleProviderInterfaceImpl2;

import java.util.Arrays;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // 根据注册中心地址和自身服务地址生成注册者对象
        Register register = new Register("localhost", 9527, ServerServiceHeapRegistry.class,
                "localhost",9528);

        // 注册实现1
        // register.register(SampleProviderInterfaceImpl.class);

        // 注册实现2
        // register.register(SampleProviderInterfaceImpl2.class);

        // 注册多个远程对象
        register.register(Arrays.asList(SampleProviderInterfaceImpl.class,
                SampleProviderInterfaceImpl2.class));

        ProviderServer server = new ProviderServer(register);

        // 启动服务提供者服务
        server.start();
    }
}
