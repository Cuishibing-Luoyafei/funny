package nx.funny.provider.server;

import lombok.Setter;
import nx.funny.provider.register.ServiceRegister;
import nx.funny.provider.register.ServiceTargetFactory;
import nx.funny.registry.ServiceType;
import nx.funny.transporter.exception.InvokeException;
import nx.funny.transporter.parameter.DefaultParameter;
import nx.funny.transporter.parameter.Parameter;
import nx.funny.transporter.request.InvokerRequest;
import nx.funny.transporter.response.DefaultInvokerResponse;
import nx.funny.transporter.response.InvokerResponse;
import nx.funny.transporter.server.InvokerRequestProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ProviderRequestProcessor implements InvokerRequestProcessor {

    @Setter
    private ServiceRegister serviceRegister;

    public ProviderRequestProcessor() {
    }

    public ProviderRequestProcessor(ServiceRegister serviceRegister) {
        this.serviceRegister = serviceRegister;
    }

    @Override
    public InvokerResponse processRequest(InvokerRequest request) {
        ServiceType type = new ServiceType(request.getName(), request.getTypeName());
        ServiceTargetFactory targetFactory = serviceRegister.getTargetFactory(type);
        Object serviceTarget = targetFactory.getServiceTarget(type);
        DefaultInvokerResponse response = new DefaultInvokerResponse();
        try {
            Method method = findMethod(serviceTarget, request);
            response.setResult(invokeMethod(method, serviceTarget, request));
        } catch (Exception e) {
            e.printStackTrace();
            response.setException(new InvokeException(e.getMessage()));
        }
        return response;
    }

    private Parameter invokeMethod(Method method, Object target, InvokerRequest request) throws InvocationTargetException, IllegalAccessException {
        List<Parameter> parameters = request.getParameters();
        if (parameters == null) {
            Object invokeResult = method.invoke(target);
            return new DefaultParameter(invokeResult.getClass(), invokeResult);
        }
        Object[] parameterValues = new Object[parameters.size()];
        int i = 0;
        for (Parameter parameter : parameters) {
            parameterValues[i++] = parameter.getValue();
        }
        Object invokeResult = method.invoke(target, parameterValues);
        if (invokeResult == null) {
            return new DefaultParameter(Void.class, null);
        }
        return new DefaultParameter(invokeResult.getClass(), invokeResult);
    }

    private Method findMethod(Object target, InvokerRequest request) throws NoSuchMethodException,
            ClassNotFoundException {
        String methodName = request.getMethodName();
        List<Parameter> parameters = request.getParameters();
        if (parameters == null) {// 没有参数
            return target.getClass().getMethod(methodName);
        }
        Class<?>[] parameterClasses = new Class<?>[parameters.size()];
        int i = 0;
        for (Parameter parameter : parameters) {
            parameterClasses[i++] = Class.forName(parameter.getType());
        }
        return target.getClass().getMethod(methodName, parameterClasses);
    }
}
