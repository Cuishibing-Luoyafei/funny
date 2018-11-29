package nx.funny.sampleprovider;

import nx.funny.provider.ServiceProvider;

@ServiceProvider
public class SampleProviderInterfaceImpl implements SampleProviderInterface {
    public String sayHello(String name) {
        return "Hello World impl1 " + name;
    }
}
