package nx.funny;

import nx.funny.consumer.DefaultProxyFactory;
import nx.funny.registry.HeapServiceRegistry;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServiceRegistry;
import nx.funny.sampleprovider.SampleProviderInterface;

import java.util.Set;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        // 根据注册中心地址生成代理工厂
        DefaultProxyFactory proxyFactory = new DefaultProxyFactory("localhost", 9527, HeapServiceRegistry.class);

        // 获取远程对象
        SampleProviderInterface service = proxyFactory.getProxy(SampleProviderInterface.class);
        for (int i = 0; i < 10; i++) {
            System.out.println(service.sayHello("FUNNY"));
        }

        // 获取注册中心远程对象
        ServiceRegistry serviceRegistry = proxyFactory.getProxy(ServiceRegistry.class);
        // 查看SampleProviderInterface在注册中心的注册情况
        Set<ServiceInfo> infoSet = serviceRegistry.retrieve(SampleProviderInterface.class.getName());
        infoSet.forEach(info -> System.out.println(String.format("ip:%s port:%s name:%s",info.getPosition().getIp(),info.getPosition().getPort(),info.getType().getTypeName())));
    }
}
