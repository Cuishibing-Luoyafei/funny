package nx.funny.provider.service.parser;

import nx.funny.annotation.ServicePositionProvider;
import nx.funny.registry.ServiceRegistry;

/**
 * 服务提供者注解解析器
 *
 * @author luoyafei
 * @date 2018-10-07
 */
public class ServiceProviderParse extends ServiceProviderScanner {

    private ServiceRegistry serviceRegistry;

    private ServicePositionProvider servicePositionProvider;

    Class<?>[] parse() {
        return new Class[0];
    }

    @Override
    Class<?>[] scan() {
        return new Class[0];
    }

}
