package nx.funny.consumer.factory;

import nx.funny.registry.ServicePosition;
import nx.funny.transporter.client.Client;
import nx.funny.transporter.client.OioClient;

import java.util.HashMap;
import java.util.Map;

public class DefaultClientFactory implements ClientFactory {

    /**
     * 缓存连接，避免每一个请求都创建一个客户端
     * key是ip+port,value是对应的客户端
     */
    private static final ThreadLocal<Map<String, Client>> CLIENT_CACHE = ThreadLocal.withInitial(() -> new HashMap<>());

    @Override
    public Client getClient(ServicePosition position) {
        return CLIENT_CACHE.get().computeIfAbsent(generateKey(position), k -> new OioClient());
    }

    @Override
    public Client removeClient(ServicePosition position) {
        return CLIENT_CACHE.get().remove(generateKey(position));
    }

    private String generateKey(ServicePosition position) {
        return position.getIp() + position.getPort();
    }
}
