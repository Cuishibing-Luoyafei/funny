package nx.funny.transporter.parameter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class DefaultParameter implements Parameter {

    private static final long serialVersionUID = 7812463405117980493L;
    
    private String type;// 参数类型名称
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
