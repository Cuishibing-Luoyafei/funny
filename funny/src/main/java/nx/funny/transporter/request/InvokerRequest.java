package nx.funny.transporter.request;

import nx.funny.transporter.parameter.Parameter;

import java.io.Serializable;
import java.util.List;

/**
 * 远程调用请求
 */
public interface InvokerRequest extends Serializable {

    /**
     * 获取服务类型
     */
    String getName();

    /**
     * 获取服务实现类
     */
    String getTypeName();

    /**
     * 获取方法名
     */
    String getMethodName();

    /**
     * 获取参数信息
     */
    List<Parameter> getParameters();
}
