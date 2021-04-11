package expression.operations.abstracted;

import expression.CommonExpression;
import expression.operations.Wrapper;

public class Negate<T> implements CommonExpression<T> {
    CommonExpression<T> value;
    Wrapper<T> wrapper;

    public Negate(CommonExpression<T> value, Wrapper<T> wrapper) {
        this.value = value;
        this.wrapper = wrapper;
    }

    public T evaluate(String x, String y, String z) {
        return wrapper.negate(value.evaluate(x, y, z));
    }

}
