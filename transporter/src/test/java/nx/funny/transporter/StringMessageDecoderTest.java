package nx.funny.transporter;


import nx.funny.transporter.exception.InvokeException;
import nx.funny.transporter.message.StringMessage;
import nx.funny.transporter.parameter.DefaultParameter;
import nx.funny.transporter.request.DefaultInvokerRequest;
import nx.funny.transporter.request.InvokerRequest;
import nx.funny.transporter.request.InvokerRequestStringMessageEncoder;
import nx.funny.transporter.request.StringMessageInvokerRequestDecoder;
import nx.funny.transporter.response.DefaultInvokerResponse;
import nx.funny.transporter.response.InvokerResponse;
import nx.funny.transporter.response.InvokerResponseStringMessageEncoder;
import nx.funny.transporter.response.StringMessageInvokerResponseDecoder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class StringMessageDecoderTest {
    private StringMessageInvokerRequestDecoder requestDecoder;
    private InvokerRequestStringMessageEncoder requestEncoder;

    private StringMessageInvokerResponseDecoder responseDecoder;
    private InvokerResponseStringMessageEncoder responseEncoder;

    @Before
    public void before() {
        requestDecoder = new StringMessageInvokerRequestDecoder();
        requestEncoder = new InvokerRequestStringMessageEncoder();

        responseDecoder = new StringMessageInvokerResponseDecoder();
        responseEncoder = new InvokerResponseStringMessageEncoder();
    }

    @Test
    public void testRequestEncodeDecode() {
        StringMessage msgParam = new StringMessage();
        msgParam.setMessageType(0);
        msgParam.setMessageBody("asdf".getBytes());
        msgParam.setMessageLength(12);

        DefaultInvokerRequest request1 = new DefaultInvokerRequest();
        request1.setType(this.getClass().getName());
        request1.setMethodName("toString");
        request1.setParameters(Arrays.asList(new DefaultParameter(Integer.class, 12),
                new DefaultParameter(String.class, "abcdefg崔士兵"),
                new DefaultParameter(StringMessage.class, msgParam),
                new DefaultParameter(Boolean.class, true),
                new DefaultParameter(Double.class, 123.456789)));
        StringMessage message = requestEncoder.encode(request1);

        InvokerRequest request2 = requestDecoder.decode(message);
        System.out.println(request1.toString());
        System.out.println(request2.toString());
        Assert.assertEquals(request1.toString(), request2.toString());
    }

    @Test
    public void testNoParameterRequestEncoder() {
        DefaultInvokerRequest request1 = new DefaultInvokerRequest();
        request1.setType(this.getClass());
        request1.setMethodName("toString");
        StringMessage message = requestEncoder.encode(request1);

        InvokerRequest request2 = requestDecoder.decode(message);
        System.out.println(request1.toString());
        System.out.println(request2.toString());
        Assert.assertEquals(request1.toString(), request2.toString());
    }

    @Test
    public void testResponseEncodeDecode() {
        DefaultInvokerResponse response1 = new DefaultInvokerResponse();
        response1.setResult(new DefaultParameter(Integer.class, 23));
        response1.setException(new InvokeException("asdf"));
        StringMessage message = responseEncoder.encode(response1);

        InvokerResponse response2 = responseDecoder.decode(message);

        System.out.println(response1.toString());
        System.out.println(response2.toString());
        Assert.assertEquals(response1.toString(), response2.toString());
    }

    @Test
    public void testNoExceptionResponseEncodeDecode() {
        DefaultInvokerResponse response1 = new DefaultInvokerResponse();
        response1.setResult(new DefaultParameter(Integer.class, 23));

        StringMessage message = responseEncoder.encode(response1);

        InvokerResponse response2 = responseDecoder.decode(message);

        System.out.println(response1.toString());
        System.out.println(response2.toString());
        Assert.assertEquals(response1.toString(), response2.toString());
    }
}
