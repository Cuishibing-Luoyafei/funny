package nx.funny.transporter;

/**
 * 远程调用通信统一消息接口
 * 定义特定协议用于netty通信
 * <p>
 * 前4字节表示消息类型
 * 中间4字节表示消息长度
 * 最后为消息内容
 */
public interface Message {

    int STRING_MESSAGE = 0;//消息体是一个字符串

    /**
     * 获取消息类型，用于明确解码的方法。
     */
    int getMessageType();

    /**
     * 获取消息体的长度。
     */
    int getMessageLength();

    /**
     * 获取消息内容
     */
    byte[] getMessageBody();
}
