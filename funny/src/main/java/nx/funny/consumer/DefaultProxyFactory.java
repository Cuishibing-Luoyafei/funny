package nx.funny.consumer;

import lombok.Getter;
import lombok.Setter;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.transporter.client.OioClient;
import nx.funny.transporter.parameter.DefaultParameter;
import nx.funny.transporter.parameter.Parameter;
import nx.funny.transporter.request.DefaultInvokerRequest;
import nx.funny.transporter.response.InvokerResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DefaultProxyFactory implements ProxyFactory {

    @Getter
    private ServiceRegistry serviceRegistry;

    @Setter
    private ServiceChooser serviceChooser;

    public DefaultProxyFactory(String registryIp,int registryPort) {
        serviceRegistry = getProxy(ServiceRegistry.class,new ServicePosition(registryIp,registryPort));
        serviceChooser = new RandomServiceChooser();
    }

    public <T> T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz}, (target, method, args) -> {
                    String name = clazz.getName();
                    Set<ServiceInfo> serviceInfos = serviceRegistry.retrieve(name);
                    ServiceInfo serviceInfo = serviceChooser.choose(serviceInfos);
                    return invoke(name, serviceInfo.getType().getTypeName(), method, serviceInfo.getPosition(), args);
                });
        return (T) o;
    }

    public <T> T getProxy(String name,Class<T> clazz) {
        Object o = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz}, (target, method, args) -> {
                    Set<ServiceInfo> serviceInfos = serviceRegistry.retrieve(name);
                    ServiceInfo serviceInfo = serviceChooser.choose(serviceInfos);
                    return invoke(name, serviceInfo.getType().getTypeName(), method, serviceInfo.getPosition(), args);
                });
        return (T) o;
    }

    private <T> T getProxy(Class<T> clazz, ServicePosition position) {
        Object o = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz}, (target, method, args) -> invoke(clazz.getName(), clazz.getName(), method, position, args));
        return (T) o;
    }

    private static Object invoke(String name, String typeName, Method method, ServicePosition position, Object[] args) throws Exception {
        try (OioClient client = new OioClient()) {
            client.connect(position.getIp(), position.getPort());
            DefaultInvokerRequest request = new DefaultInvokerRequest();
            request.setName(name);
            request.setTypeName(typeName);
            request.setMethodName(method.getName());
            request.setParameters(getParametersFromArgs(method, args));
            InvokerResponse response = client.sendRequest(request);
            return response.getResult().getValue();
        }
    }

    private static List<Parameter> getParametersFromArgs(Method method, Object[] args) {
        if (args == null || args.length == 0)
            return null;
        List<Parameter> parameters = new ArrayList<>(args.length);
        Class<?>[] parameterTypes = method.getParameterTypes();
        int i = 0;
        for (Object o : args) {
            parameters.add(new DefaultParameter(parameterTypes[i++], o));
        }
        return parameters;
    }

}
