package nx.funny.sampleprovider;

import nx.funny.provider.ServiceProvider;

@ServiceProvider
public class SampleProviderInterfaceImpl2 implements SampleProviderInterface {
    public String sayHello(String name) {
        return "Hello World impl2 "+name;
    }
}
