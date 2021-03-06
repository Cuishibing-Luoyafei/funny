package nx.funny.transporter;

import nx.funny.transporter.message.Message;

/**
 * 消息编码者，把T类型的数据编码成Message
 */
public interface MessageEncoder<T, M extends Message> {

    M encode(T t);

}
