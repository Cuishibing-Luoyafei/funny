package nx.funny.transporter;


import nx.funny.transporter.exception.InvokeException;
import nx.funny.transporter.message.DefaultMessage;
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

public class DefaultMessageDecoderTest {
    private JdkMessageTranslator<InvokerRequest> requestTranslator;

    private JdkMessageTranslator<InvokerResponse> responseTranslator;

    @Before
    public void before() {
        requestTranslator = new JdkMessageTranslator<>();

        responseTranslator = new JdkMessageTranslator<>();
    }

    @Test
    public void testRequestEncodeDecode() {
        DefaultMessage msgParam = new DefaultMessage();
        msgParam.setMessageType(0);
        msgParam.setMessageBody("asdf".getBytes());
        msgParam.setMessageLength(12);

        DefaultInvokerRequest request1 = new DefaultInvokerRequest();
        request1.setName(this.getClass().getName());
        request1.setMethodName("toString");
        request1.setParameters(Arrays.asList(new DefaultParameter(Integer.class, 12),
                new DefaultParameter(String.class, "abcdefg崔士兵"),
                new DefaultParameter(DefaultMessage.class, msgParam),
                new DefaultParameter(Boolean.class, true),
                new DefaultParameter(Double.class, 123.456789)));
        Message message = requestTranslator.encode(request1);

        InvokerRequest request2 = requestTranslator.decode(message);
        System.out.println(request1.toString());
        System.out.println(request2.toString());
        Assert.assertEquals(request1.toString(), request2.toString());
    }

    @Test
    public void testNoParameterRequestEncoder() {
        DefaultInvokerRequest request1 = new DefaultInvokerRequest();
        request1.setName(this.getClass());
        request1.setMethodName("toString");
        Message message = requestTranslator.encode(request1);

        InvokerRequest request2 = requestTranslator.decode(message);
        System.out.println(request1.toString());
        System.out.println(request2.toString());
        Assert.assertEquals(request1.toString(), request2.toString());
    }

    @Test
    public void testResponseEncodeDecode() {
        DefaultInvokerResponse response1 = new DefaultInvokerResponse();
        response1.setResult(new DefaultParameter(Integer.class, 23));
        response1.setException(new InvokeException("asdf"));
        Message message = responseTranslator.encode(response1);

        InvokerResponse response2 = responseTranslator.decode(message);

        System.out.println(response1.toString());
        System.out.println(response2.toString());
        Assert.assertEquals(response1.toString(), response2.toString());
    }

    @Test
    public void testNoExceptionResponseEncodeDecode() {
        DefaultInvokerResponse response1 = new DefaultInvokerResponse();
        response1.setResult(new DefaultParameter(Integer.class, 23));

        Message message = responseTranslator.encode(response1);

        InvokerResponse response2 = responseTranslator.decode(message);

        System.out.println(response1.toString());
        System.out.println(response2.toString());
        Assert.assertEquals(response1.toString(), response2.toString());
    }
}
