package nx.funny.provider;

import nx.funny.registry.ServicePosition;

/**
 * 提供整个服务的地址信息
 */
public interface ServicePositionProvider {
    ServicePosition getServicePosition();
}
