package nx.funny.sampleprovider;

@ServiceProvider("SampleProviderInterface")
public class SampleProviderInterfaceImpl2 implements SampleProviderInterface {
    public String sayHello(String name) {
        return "Hello World impl2 "+name;
    }
}
