package nx.funny.consumer;

import lombok.Setter;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServiceRegistry;

import java.lang.reflect.Method;
import java.util.Set;

public class DefaultInvocationHandler extends SpecificInvocationHandler {

    @Setter
    private String serviceName;

    @Setter
    private ServiceRegistry serviceRegistry;

    @Setter
    private ServiceChooser serviceChooser;

    public DefaultInvocationHandler(String serviceName,
                                    ServiceRegistry serviceRegistry, ServiceChooser serviceChooser) {
        this.serviceName = serviceName;
        this.serviceRegistry = serviceRegistry;
        this.serviceChooser = serviceChooser;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Set<ServiceInfo> serviceInfos = serviceRegistry.retrieve(serviceName);
        ServiceInfo serviceInfo = serviceChooser.choose(serviceInfos);
        return invoke(serviceInfo, method, args);
    }
}
