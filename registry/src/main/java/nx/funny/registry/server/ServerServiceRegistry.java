package nx.funny.registry.server;

import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.registry.ServiceType;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ServiceRegistry的服务端实现
 */
public class ServerServiceRegistry implements ServiceRegistry {

    private final ConcurrentHashMap<ServiceType, Set<ServicePosition>> serviceRegistry = new ConcurrentHashMap<>();
    public static ServiceRegistry INSTANCE = new ServerServiceRegistry();

    private ServerServiceRegistry() {
    }
    @Override
    public void register(ServiceInfo info) {
        ServiceType type = info.getType();
        ServicePosition position = info.getPosition();
        synchronized (serviceRegistry) {
            Set<ServicePosition> container = serviceRegistry.get(type);
            if (container == null) {
                container = new HashSet<>();
                container.add(position);
                serviceRegistry.put(type, container);
                return;
            }
        }
        Set<ServicePosition> container = serviceRegistry.get(type);
        synchronized (container) {
            container.remove(position);
            container.add(position);
        }
    }

    @Override
    public void remove(ServiceInfo info) {
        Set<ServicePosition> positions = serviceRegistry.get(info.getType());
        positions.remove(info.getPosition());
    }

    @Override
    public void removeAll(ServiceType type) {
        serviceRegistry.remove(type);
    }

    @Override
    public Set<ServicePosition> retrieve(ServiceType type) {
        return serviceRegistry.get(type);
    }
}
