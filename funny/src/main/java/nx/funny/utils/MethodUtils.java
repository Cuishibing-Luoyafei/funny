package nx.funny.utils;

import nx.funny.transporter.parameter.Parameter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MethodUtils {

    private static final Map<MethodCacheKey, Method> METHOD_CACHE = new HashMap<>();

    /**
     * 根据方法名和参数列表查找方法
     */
    public static Method findMethod(Class<?> target, String methodName, List<Parameter> parameters)
            throws ClassNotFoundException, NoSuchMethodException {
        MethodCacheKey methodCacheKey = new MethodCacheKey(target, methodName, parameters);
        Method method = METHOD_CACHE.get(methodCacheKey);
        if (method != null) {
            return method;
        }
        Class<?>[] parameterClasses = new Class<?>[parameters.size()];
        int i = 0;
        for (Parameter parameter : parameters) {
            parameterClasses[i++] = Class.forName(parameter.getType());
        }
        method = target.getMethod(methodName, parameterClasses);
        METHOD_CACHE.put(methodCacheKey, method);
        return method;
    }

    private static final class MethodCacheKey {
        private Class<?> target;
        private String methodName;
        private List<Parameter> parameters;

        private int hash = 0;

        public MethodCacheKey(Class<?> target, String methodName, List<Parameter> parameters) {
            this.target = target;
            this.methodName = methodName;
            this.parameters = parameters;
        }

        @Override
        public int hashCode() {
            if (hash != 0) {
                return hash;
            }

            int result = 1;

            result = 31 * result + target.hashCode();
            result = 31 * result + methodName.hashCode();

            if (parameters != null) {
                for (Parameter p : parameters) {
                    result = 31 * result + p.getType().hashCode();
                }
            }
            hash = result;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof MethodCacheKey) {
                MethodCacheKey other = (MethodCacheKey) obj;
                if (hashCode() != other.hashCode()) {
                    return false;
                }
                boolean result = false;

                result = Objects.equals(target, other.target);
                if (!result) {
                    return false;
                }

                result = Objects.equals(methodName, other.methodName);
                if (!result) {
                    return false;
                }
                List<Parameter> otherParameters = other.parameters;
                if (parameters == null && otherParameters == null) {
                    return true;
                }
                if (parameters == null || otherParameters == null) {
                    return false;
                }
                if (parameters.size() != otherParameters.size()) {
                    return false;
                }
                int size = parameters.size();
                for (int i = 0; i < size; ++i) {
                    result = Objects.equals(parameters.get(i).getType(), otherParameters.get(i).getType());
                    if (!result) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }
}
