package nx.funny.transporter;

import nx.funny.transporter.message.Message;

public interface MessageDecoder<T extends Message, K> {
    K decode(T message);
}
