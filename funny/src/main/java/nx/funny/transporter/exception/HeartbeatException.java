package nx.funny.transporter.exception;

import lombok.NoArgsConstructor;

/**
 * @author yafei10@staff.weibo.com
 */
@NoArgsConstructor
public class HeartbeatException extends RuntimeException {

    private static final long serialVersionUID = 7414831747121955335L;

    public HeartbeatException(String message, Exception ex) {
        super(message, ex);
    }

}
