package nx.funny.registry;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ServiceRegistry的服务端实现
 */
public class ServerServiceRegistry implements ServiceRegistry {

    private ConcurrentHashMap<Class<?>, Set<ServiceInfo>> services = new ConcurrentHashMap<>();

    public void register(ServiceInfo info) {
        Class<?> clazz = info.getClazz();
        Set<ServiceInfo> serviceInfos = services.get(clazz);
        if (serviceInfos == null) {
            Set<ServiceInfo> infos = new HashSet<>();
            infos.add(info);
            services.put(clazz, infos);
        } else {
            if (serviceInfos.contains(info)) {
                serviceInfos.remove(info);
            }
            serviceInfos.add(info);
        }
    }

    public void remove(ServiceInfo info) {
        Set<ServiceInfo> serviceInfos = services.get(info.getClazz());
        serviceInfos.remove(info);
    }

    public Set<ServiceInfo> retrive(Class<?> serviceClass) {
        return services.get(serviceClass);
    }
}
