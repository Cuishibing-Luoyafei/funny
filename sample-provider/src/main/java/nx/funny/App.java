package nx.funny;

import nx.funny.sampleprovider.SampleProviderInterfaceImpl;
import nx.funny.sampleprovider.SampleProviderInterfaceImpl2;
import nx.funny.sampleprovider.register.Register;
import nx.funny.sampleprovider.server.ProviderServer;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Register register = new Register("localhost",9527,
                "localhost",9528);
        register.register(SampleProviderInterfaceImpl.class);
        register.register(SampleProviderInterfaceImpl2.class);

        ProviderServer server = new ProviderServer(register);
        server.start();
    }
}
