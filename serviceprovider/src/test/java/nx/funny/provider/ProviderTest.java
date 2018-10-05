package nx.funny.provider;/**
 * @author yafei10
 * @date new Date()
 */

import nx.funny.provider.service.IServiceProvider;
import nx.funny.provider.service.impl.ServiceProviderImpl;
import org.junit.Test;

/**
 *
 * @author luoyafei
 * @date 2018-10-05
 */
public class ProviderTest {

    @Test
    public void test() {
        IServiceProvider provider = new ServiceProviderImpl();
        provider.register();
    }
}
