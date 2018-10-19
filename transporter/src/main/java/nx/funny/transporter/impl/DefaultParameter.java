package nx.funny.transporter.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nx.funny.transporter.Parameter;


@Getter
@Setter
@ToString
public class DefaultParameter implements Parameter {

    private String type;
    private Object value;

    public DefaultParameter() {
    }

    public DefaultParameter(Class<?> clazz, Object value) {
        this(clazz.getName(), value);
    }

    public DefaultParameter(String type, Object value) {
        this.type = type;
        this.value = value;
    }
}
