package nx.funny.transporter;


import nx.funny.transporter.exception.InvokeException;
import nx.funny.transporter.message.Message;
import nx.funny.transporter.message.StringMessage;
import nx.funny.transporter.parameter.DefaultParameter;
import nx.funny.transporter.request.DefaultInvokerRequest;
import nx.funny.transporter.request.InvokerRequestStringMessageEncoder;
import nx.funny.transporter.response.DefaultInvokerResponse;
import nx.funny.transporter.response.InvokerResponseStringMessageEncoder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class StringMessageEncoderTest {
    private InvokerRequestStringMessageEncoder requestEncoder;
    private InvokerResponseStringMessageEncoder responseEncoder;

    @Before
    public void before() {
        requestEncoder = new InvokerRequestStringMessageEncoder();
        responseEncoder = new InvokerResponseStringMessageEncoder();
    }

    @Test
    public void testInvokerRequestEncode() {
        DefaultInvokerRequest request = new DefaultInvokerRequest();
        request.setType(this.getClass().getName());
        request.setMethodName("toString");
        request.setParameters(Arrays.asList(new DefaultParameter(Integer.class.getName(), 12)));
        StringMessage message = requestEncoder.encode(request);
        System.out.println(message.getMessage());
        Assert.assertEquals(message.getMessageType(), Message.STRING_MESSAGE);
    }

    @Test
    public void testInvokerResponseEncode() {
        DefaultInvokerResponse response = new DefaultInvokerResponse();
        response.setException(new InvokeException("aaa"));
        response.setResult(new DefaultParameter(Integer.class, 12));
        StringMessage message = responseEncoder.encode(response);
        System.out.println(message.getMessage());
    }
}
