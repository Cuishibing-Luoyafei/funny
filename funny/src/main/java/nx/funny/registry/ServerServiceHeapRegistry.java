package nx.funny.registry;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServiceRegistry的服务端实现
 */
public class ServerServiceHeapRegistry implements ServiceRegistry {

    private Logger logger = Logger.getLogger(getClass().getName());

    private final ConcurrentHashMap<ServiceType, Set<ServicePosition>> serviceRegistry = new ConcurrentHashMap<>();

    public ServerServiceHeapRegistry() {
    }

    private Set<ServicePosition> getPositionContainer(ServiceType type) {
        return serviceRegistry.computeIfAbsent(type, t -> {
            return new HashSet<>();
        });
    }

    @Override
    public void register(ServiceInfo info) {
        ServiceType type = info.getType();
        ServicePosition position = info.getPosition();

        Set<ServicePosition> container = getPositionContainer(type);
        synchronized (container) {
            container.add(position);
            logger.log(Level.INFO, "register:" + info.toString());
        }
    }

    @Override
    public void remove(ServiceInfo info) {
        Set<ServicePosition> container = getPositionContainer(info.getType());
        synchronized (container) {
            container.remove(info.getPosition());
        }
        logger.log(Level.INFO, "remove:" + info.toString());
    }

    @Override
    public void removeAll(ServiceType type) {
        serviceRegistry.remove(type);
        logger.log(Level.INFO, "removeAll:" + type.toString());
    }

    @Override
    public Set<ServiceInfo> retrieve(String name) {
        Set<ServiceInfo> result = new HashSet<>();
        Set<Map.Entry<ServiceType, Set<ServicePosition>>> entries = serviceRegistry.entrySet();
        entries.forEach(entry -> {
            ServiceType key = entry.getKey();
            if (key.getName().equals(name)) {
                Set<ServicePosition> positions = entry.getValue();
                positions.forEach(position -> {
                    result.add(new ServiceInfo(key, position));
                });
            }
        });
        logger.log(Level.INFO, "retrieve:" + name);
        return result;
    }

    @Override
    public void register(List<ServiceInfo> services) {
        if (services == null)
            return;
        services.forEach(this::register);
    }
}
