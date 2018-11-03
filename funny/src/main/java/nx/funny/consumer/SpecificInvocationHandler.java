package nx.funny.consumer;

import lombok.Setter;
import nx.funny.registry.ServiceInfo;
import nx.funny.registry.ServicePosition;
import nx.funny.registry.ServiceType;
import nx.funny.transporter.client.OioClient;
import nx.funny.transporter.parameter.DefaultParameter;
import nx.funny.transporter.parameter.Parameter;
import nx.funny.transporter.request.DefaultInvokerRequest;
import nx.funny.transporter.response.InvokerResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SpecificInvocationHandler implements InvocationHandler {

    @Setter
    private ServiceInfo serviceInfo;

    public SpecificInvocationHandler() {
    }

    public SpecificInvocationHandler(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invoke(serviceInfo, method, args);
    }

    protected Object invoke(ServiceInfo serviceInfo, Method method, Object[] args) throws Exception {
        ServiceType type = serviceInfo.getType();
        ServicePosition position = serviceInfo.getPosition();
        try (OioClient client = new OioClient()) {
            client.connect(position.getIp(), position.getPort());
            DefaultInvokerRequest request = new DefaultInvokerRequest();
            request.setName(type.getName());
            request.setTypeName(type.getTypeName());
            request.setMethodName(method.getName());
            request.setParameters(getParameters(method, args));
            InvokerResponse response = client.sendRequest(request);
            return response.getResult().getValue();
        }
    }

    private static List<Parameter> getParameters(Method method, Object[] args) {
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
