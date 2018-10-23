package nx.funny.sampleprovider;

import java.lang.annotation.*;

/**
 * 被这个注解注释的类被视为一个要被远程调用的类（服务提供者）
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceProvider {
    String value();
    /**
     * 标明该服务提供者的名称
     * 如果没有提供，那么默认为类的全限定名称
     */
    String name() default "";

    /**
     * 标明服务提供者的实现类的名称
     * 如果没有提供，那么默认为类的全限定名称
     */
    String typeName() default "";
}
