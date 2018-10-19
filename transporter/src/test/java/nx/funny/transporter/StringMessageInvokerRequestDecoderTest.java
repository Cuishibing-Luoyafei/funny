package nx.funny.provider.transport;

import nx.funny.provider.invoker.InvokerRequest;
import nx.funny.provider.invoker.impl.DefaultInvokerRequest;
import nx.funny.provider.invoker.impl.DefaultParameter;
import nx.funny.transporter.impl.InvokerRequestStringMessageEncoder;
import nx.funny.transporter.impl.StringMessage;
import nx.funny.transporter.impl.StringMessageInvokerRequestDecoder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class StringMessageInvokerRequestDecoderTest {
    private StringMessageInvokerRequestDecoder decoder;
    private InvokerRequestStringMessageEncoder encoder;

    @Before
    public void before() {
        decoder = new StringMessageInvokerRequestDecoder();
        encoder = new InvokerRequestStringMessageEncoder();
    }

    @Test
    public void testDecode() {
        StringMessage msgParam = new StringMessage();
        msgParam.setMessageType(0);
        msgParam.setMessageBody("asdf".getBytes());
        msgParam.setMessageLength(12);

        DefaultInvokerRequest request1 = new DefaultInvokerRequest();
        request1.setServiceType(this.getClass().getName());
        request1.setMethodName("toString");
        request1.setParameters(Arrays.asList(new DefaultParameter(Integer.class, 12),
                new DefaultParameter(String.class, "abcdefg崔士兵"),
                new DefaultParameter(StringMessage.class, msgParam)));
        StringMessage message = encoder.encode(request1);

        InvokerRequest request2 = decoder.decode(message);
        System.out.println(request1.toString());
        System.out.println(request2.toString());
        Assert.assertEquals(request1.toString(), request2.toString());
    }
}
