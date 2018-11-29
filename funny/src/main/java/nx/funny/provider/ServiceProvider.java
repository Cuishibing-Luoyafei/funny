package nx.funny.provider;

import java.lang.annotation.*;

/**
 * 被这个注解注释的类被视为一个要被远程调用的类（服务提供者）
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceProvider {

    /**
     * 标注服务提供者的接口,因为有可能一个类实现了多个接口需要明确指定一个.
     * 如果没有指定则使用类实现的第一个接口
     * */
    Class<?> interFace() default ServiceProvider.class;
}
