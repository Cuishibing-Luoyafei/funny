package nx.funny.registry.common;

import java.nio.charset.Charset;

public class Constant {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static final char REQUEST_DELIMITER_CR = '\r';
    public static final char REQUEST_DELIMITER_NL = '\n';
    public static final String REQUEST_DELIMITER = new String(new char[]{REQUEST_DELIMITER_CR, REQUEST_DELIMITER_NL});
}
