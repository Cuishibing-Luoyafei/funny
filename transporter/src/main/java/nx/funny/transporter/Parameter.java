package nx.funny.transporter;

/**
 * 方法调用的参数和返回值
 * T 为表示参数值使用的类型。例如：使用JSON形式传递，T就为String等
 */
public interface Parameter {
    /**
     * 参数的类型，类型的全限定名称
     */
    String getType();

    /**
     * 参数的值
     */
    Object getValue();
}
