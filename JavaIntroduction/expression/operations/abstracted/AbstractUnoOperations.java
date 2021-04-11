package expression.operations.abstracted;

import expression.CommonExpression;
import expression.operations.Wrapper;

public abstract class AbstractUnoOperations<T> implements CommonExpression<T> {
    private CommonExpression<T> argument;
    protected Wrapper<T> wrapper;

    public AbstractUnoOperations(CommonExpression<T> argument, Wrapper<T> wrapper) {
        this.argument = argument;
        this.wrapper = wrapper;
    }

    public T evaluate(String x, String y, String z) {
        T first = argument.evaluate(x, y, z);
        return evaluate(first);
    }
    protected abstract T evaluate(T first);
}
