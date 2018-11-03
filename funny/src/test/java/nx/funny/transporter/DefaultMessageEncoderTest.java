package nx.funny.transporter;


import nx.funny.transporter.exception.InvokeException;
import nx.funny.transporter.message.Message;
import nx.funny.transporter.parameter.DefaultParameter;
import nx.funny.transporter.request.DefaultInvokerRequest;
import nx.funny.transporter.request.InvokerRequest;
import nx.funny.transporter.request.JdkMessageTranslator;
import nx.funny.transporter.response.DefaultInvokerResponse;
import nx.funny.transporter.response.InvokerResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class DefaultMessageEncoderTest {
    private JdkMessageTranslator<InvokerRequest> requestEncoder;
    private JdkMessageTranslator<InvokerResponse> responseEncoder;

    @Before
    public void before() {
        requestEncoder = new JdkMessageTranslator<>();
        responseEncoder = new JdkMessageTranslator<>();
    }

    @Test
    public void testInvokerRequestEncode() {
        DefaultInvokerRequest request = new DefaultInvokerRequest();
        request.setName(this.getClass().getName());
        request.setMethodName("toString");
        request.setParameters(Arrays.asList(new DefaultParameter(Integer.class.getName(), 12)));
        Message message = requestEncoder.encode(request);
        System.out.println(message.toString());
        Assert.assertEquals(message.getMessageType(), Message.JDK_MESSAGE);
    }

    @Test
    public void testInvokerResponseEncode() {
        DefaultInvokerResponse response = new DefaultInvokerResponse();
        response.setException(new InvokeException("aaa"));
        response.setResult(new DefaultParameter(Integer.class, 12));
        Message message = responseEncoder.encode(response);
        System.out.println(message.toString());
    }
}
