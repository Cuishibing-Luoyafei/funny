package nx.funny.transporter;

public interface MessageDecoder<T extends Message, K> {
    K decode(T message);
}
