package nx.funny.provider;

/**
 * 提供整个服务的地址信息
 * */
public interface ServicePositionProvider {
    String getIp();
    int getPort();
}
