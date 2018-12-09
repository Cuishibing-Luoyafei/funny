package nx.funny.transporter.exception;

import lombok.NoArgsConstructor;

/**
 * @author yafei10@staff.weibo.com
 */
@NoArgsConstructor
public class HeartbeatException extends RuntimeException {

    public HeartbeatException(String message, Exception ex) {
        super(message, ex);
    }

}
