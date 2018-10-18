package nx.funny.provider.invoker.impl;

import nx.funny.provider.invoker.Parameter;

public class DefaultParameter implements Parameter {

    private String type;
    private Object value;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }


}
