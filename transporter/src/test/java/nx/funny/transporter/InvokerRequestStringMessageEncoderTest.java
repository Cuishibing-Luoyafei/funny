package nx.funny.transporter;


import nx.funny.transporter.impl.DefaultInvokerRequest;
import nx.funny.transporter.impl.DefaultParameter;
import nx.funny.transporter.impl.InvokerRequestStringMessageEncoder;
import nx.funny.transporter.impl.StringMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class InvokerRequestStringMessageEncoderTest {
    private InvokerRequestStringMessageEncoder messageEncoder;

    @Before
    public void before() {
        messageEncoder = new InvokerRequestStringMessageEncoder();
    }

    @Test
    public void testEncode() {
        DefaultInvokerRequest request = new DefaultInvokerRequest();
        request.setServiceType(this.getClass().getName());
        request.setMethodName("toString");
        request.setParameters(Arrays.asList(new DefaultParameter(Integer.class.getName(), 12)));
        StringMessage message = messageEncoder.encode(request);
        System.out.println(message.getMessage());
    }
}
