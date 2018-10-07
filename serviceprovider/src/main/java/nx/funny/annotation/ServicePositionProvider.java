package nx.funny.annotation;

/**
 * 获取整块服务的ip和端口
 *
 * @author luoyafei
 * @date 2018-10-07
 */
public interface ServicePositionProvider {

    String getIp();

    int getPort();

}

