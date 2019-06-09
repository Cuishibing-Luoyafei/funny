package nx.funny.transporter.client;

import nx.funny.transporter.request.InvokerRequest;
import nx.funny.transporter.response.InvokerResponse;

import java.io.Closeable;
import java.io.IOException;

public interface Client extends Closeable {
    /**
     * 连接到服务端
     */
    void connect(String ip, int port) throws IOException;

    /**
     * 是否连接
     */
    boolean connected();

    /**
     * 发送远程调用请求并获取响应
     */
    InvokerResponse sendRequest(InvokerRequest request) throws IOException;

    /**
     * 关闭连接
     */
    void close() throws IOException;
}
