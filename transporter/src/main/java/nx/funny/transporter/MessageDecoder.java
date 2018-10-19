package nx.funny.provider.transport;

public interface MessageDecoder<T extends Message, K> {
    K decode(T message);
}
