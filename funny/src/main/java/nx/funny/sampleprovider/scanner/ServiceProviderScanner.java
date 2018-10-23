package nx.funny.sampleprovider.scanner;

import java.util.List;

/**
 * 搜索带有ServiceProvider注解的类
 */
public interface ServiceProviderScanner {

    /**
     * 搜索给定类所在包下以及子包下的所有目标
     *
     * @param baseClass 要搜索的类型
     * @param exclude   要剔除的类型集合
     */
    List<String> scan(Class<?> baseClass, String... exclude);

    /**
     * 搜索给定包名下的以及子包下的所有目标
     *
     * @param basePackage 要搜索的包名
     * @param exclude     要剔除的类型集合
     */
    List<String> scan(String basePackage, String... exclude);
}
