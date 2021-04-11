package expression.operations.abstracted;

import expression.CommonExpression;
import expression.operations.Wrapper;

public class Const<T> implements CommonExpression<T> {
    private String value;
    private Wrapper<T> wrapper;

    public Const(String value, Wrapper<T> wrapper) {
        this.value = value;
        this.wrapper = wrapper;
    }

    public T evaluate(String x, String y, String z) {
        return wrapper.parse(value);
    }
}