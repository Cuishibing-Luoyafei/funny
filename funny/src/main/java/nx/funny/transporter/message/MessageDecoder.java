package nx.funny.transporter.message;

public interface MessageDecoder<T extends Message, K> {
    K decode(T message);
}
