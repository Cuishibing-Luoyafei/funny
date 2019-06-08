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
import nx.funny.utils.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ProviderRequestProcessor implements InvokerRequestProcessor {

    private ServiceRegister serviceRegister;

    public ProviderRequestProcessor(ServiceRegister serviceRegister) {
        this.serviceRegister = serviceRegister;
    }

    @Override
    public InvokerResponse processRequest(InvokerRequest request) {
        ServiceType type = ServiceType.valueOf(request.getName(), request.getTypeName());
        ServiceTargetFactory targetFactory = serviceRegister.getTargetFactory(type);
        Object serviceTarget = targetFactory.getServiceTarget(type);
        DefaultInvokerResponse response = new DefaultInvokerResponse();
        try {
            Method method = MethodUtils.findMethod(serviceTarget.getClass(), request.getMethodName(), request.getParameters());
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

}
