package nx.funny.transporter.parameter;

import java.io.Serializable;

/**
 * 用来表示方法的参数和返回值
 */
public interface Parameter extends Serializable {
    /**
     * 参数的类型，类型的全限定名称
     */
    String getType();

    /**
     * 参数的值
     */
    Object getValue();
}
