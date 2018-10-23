package nx.funny.sampleprovider.register;

import nx.funny.registry.ServiceType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 第一次初始化对象，之后就不再新创建了
 * 使用默认构造函数创建对象
 */
public class FirstNewTargetFactory implements ServiceTargetFactory {

    private static final FirstNewTargetFactory INSTANCE = new FirstNewTargetFactory();

    public static ServiceTargetFactory INSTANCE() {
        return INSTANCE;
    }

    private Map<ServiceType, Object> targetPool = new ConcurrentHashMap<>();

    private FirstNewTargetFactory() {
    }

    @Override
    public Object getServiceTarget(ServiceType type) {
        Object target = targetPool.get(type);
        if (target == null) {
            target = getInstance(type);
            targetPool.put(type, target);
        }
        return target;
    }

    private Object getInstance(ServiceType type) {
        String typeName = type.getTypeName();
        try {
            Class<?> clazz = Class.forName(typeName);
            return clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
