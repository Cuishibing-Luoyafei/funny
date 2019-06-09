package nx.funny.consumer.factory;

import nx.funny.registry.ServicePosition;
import nx.funny.transporter.client.Client;

/**
 * 获取Client
 */
public interface ClientFactory {

    Client getClient(ServicePosition position);

    Client removeClient(ServicePosition position);
}
