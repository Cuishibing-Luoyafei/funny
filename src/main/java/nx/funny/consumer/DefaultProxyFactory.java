package nx.funny.consumer;

import lombok.Getter;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceRegistry;
import nx.funny.transporter.client.OioClient;
import nx.funny.transporter.parameter.DefaultParameter;
import nx.funny.transporter.parameter.Parameter;
import nx.funny.transporter.request.DefaultInvokerRequest;
import nx.funny.transporter.response.InvokerResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DefaultProxyFactory implements ProxyFactory {

    @Getter
    private ServiceRegistry serviceRegistry;

    public DefaultProxyFactory(String registryIp,int registryPort) {
        serviceRegistry = getProxy(ServiceRegistry.class,new ServicePosition(registryIp,registryPort));
    }

//    public DefaultProxyFactory(ServiceRegistry serviceRegistry) {
//        this.serviceRegistry = serviceRegistry;
//    }

    public <T> T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz}, new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String name = clazz.getName();
                        Set<ServiceInfo> serviceInfos = serviceRegistry.retrieve(name);
                        ServiceInfo serviceInfo = (ServiceInfo) serviceInfos.toArray()[new Random().nextInt(serviceInfos.size())];
                        return DefaultProxyFactory.invoke(name,serviceInfo.getType().getTypeName(), method.getName(), serviceInfo.getPosition(), args);
                    }
                });
        return (T) o;
    }

    private <T> T getProxy(Class<T> clazz, ServicePosition position) {
        Object o = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz}, new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return DefaultProxyFactory.invoke(clazz.getName(),clazz.getName(), method.getName(), position, args);
                    }
                });
        return (T) o;
    }

    private static Object invoke(String type, String typeName,String methodName, ServicePosition position, Object[] args) throws Exception {
        OioClient client = new OioClient();
        try {
            client.connect(position.getIp(), position.getPort());
            DefaultInvokerRequest request = new DefaultInvokerRequest();
            request.setType(type);
            request.setTypeName(typeName);
            request.setMethodName(methodName);
            request.setParameters(getParametersFromArgs(args));
            InvokerResponse response = client.sendRequest(request);
            return response.getResult().getValue();
        } finally {
            client.close();
        }
    }

    private static List<Parameter> getParametersFromArgs(Object[] args) {
        if (args == null || args.length == 0)
            return null;
        List<Parameter> parameters = new ArrayList<>(args.length);
        for (Object o : args) {
            parameters.add(new DefaultParameter(o.getClass(), o));
        }
        return parameters;
    }

}
