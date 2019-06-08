package nx.funny.transporter.server;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractServer implements Server {
    /**
     * 绑定的ip地址
     * */
    protected String bindIp;
    /**
     * 监听的端口
     * */
    protected int port;

}
