package nx.funny.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

/**
 * 服务提供者标注
 *
 * @author luoyafei
 * @date 2018-10-05
 */
public @interface FunnyServiceProvider {

    /**
     * 服务名称
     */
    String serviceName();

    /**
     * 注册中心地址(ip:port)
     */
    String serverServiceRegistryPath();

}
