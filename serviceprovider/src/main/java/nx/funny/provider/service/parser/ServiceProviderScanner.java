package nx.funny.provider.service.parser;

/**
 * 服务提供者扫描抽象类
 *
 * @author luoyafei
 * @date 2018-10-07
 */
abstract class ServiceProviderScanner {
    abstract Class<?>[] scan();
}
