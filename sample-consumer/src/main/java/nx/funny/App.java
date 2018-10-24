package nx.funny;

import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServiceRegistry;
import nx.funny.sampleconsumer.DefaultProxyFactory;
import nx.funny.sampleprovider.SampleProviderInterface;

import java.util.Set;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        DefaultProxyFactory proxyFactory = new DefaultProxyFactory("localhost", 9527);
        SampleProviderInterface service = proxyFactory.getProxy(SampleProviderInterface.class);
        for (int i = 0; i < 10; i++) {
            System.out.println(service.sayHello("FUNNY"));
        }

        ServiceRegistry serviceRegistry = proxyFactory.getProxy(ServiceRegistry.class);
        // 查看SampleProviderInterface在注册中心的注册情况
        Set<ServiceInfo> infoSet = serviceRegistry.retrieve(SampleProviderInterface.class.getName());
        infoSet.forEach(info->{
            System.out.println(info.getType().getTypeName());
        });
    }
}
