package nx.funny.transporter.server;

import nx.funny.transporter.request.InvokerRequest;
import nx.funny.transporter.response.InvokerResponse;

/**
 * 处理InvokerRequest
 * 请求的处理过程必须是线程安全的
 */
public interface InvokerRequestProcessor {

    InvokerResponse processRequest(InvokerRequest request);

}
