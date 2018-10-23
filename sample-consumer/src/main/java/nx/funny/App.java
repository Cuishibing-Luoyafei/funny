package nx.funny;

import nx.funny.sampleconsumer.DefaultProxyFactory;
import nx.funny.sampleprovider.SampleProviderInterface;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        DefaultProxyFactory proxyFactory = new DefaultProxyFactory("localhost",9527);
        SampleProviderInterface service = proxyFactory.getProxy(SampleProviderInterface.class);
        System.out.println(service.sayHello("Funny"));
    }
}
