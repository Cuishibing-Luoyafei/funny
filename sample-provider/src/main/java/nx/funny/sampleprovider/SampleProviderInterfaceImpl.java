package nx.funny.sampleprovider;

@ServiceProvider("SampleProviderInterface")
public class SampleProviderInterfaceImpl implements SampleProviderInterface {
    public String sayHello(String name) {
        return "Hello World impl1 "+name;
    }
}
