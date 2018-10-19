package nx.funny.transporter;

import java.util.List;

/**
 * 远程调用请求
 */
public interface InvokerRequest {

    /**
     * 获取服务类型
     */
    String getServiceType();

    /**
     * 获取方法名
     */
    String getMethodName();

    /**
     * 获取参数信息
     */
    List<Parameter> getParameters();
}
