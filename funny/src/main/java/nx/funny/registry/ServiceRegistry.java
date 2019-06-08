package nx.funny.registry;

import nx.funny.transporter.message.HeartBeatRequest;
import nx.funny.transporter.message.HeartBeatResponse;
import java.util.List;
import java.util.Set;

public interface ServiceRegistry {

    void register(ServiceInfo info);

    void register(List<ServiceInfo> services);

    void remove(ServiceInfo info);

    void removeAll(ServiceType type);

    Set<ServiceInfo> retrieve(String name);

    HeartBeatResponse receiveHeartbeat(Set<HeartBeatRequest> services);

}
