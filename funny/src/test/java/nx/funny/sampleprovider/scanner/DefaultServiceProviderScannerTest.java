package nx.funny.sampleprovider.scanner;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DefaultServiceProviderScannerTest {

    private DefaultServiceProviderScanner serviceProviderScanner;

    @Before
    public void before(){
        serviceProviderScanner = new DefaultServiceProviderScanner();
    }

    @Test
    public void testScan(){
        List<String> result = serviceProviderScanner.scan(this.getClass());
        result.forEach(System.out::println);
    }

}
