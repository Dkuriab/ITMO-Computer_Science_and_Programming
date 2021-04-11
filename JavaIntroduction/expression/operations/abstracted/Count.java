package expression.operations.abstracted;

import expression.CommonExpression;
import expression.operations.Wrapper;

public class Count<T> extends AbstractUnoOperations<T> {

    public Count(CommonExpression<T> argument, Wrapper<T> wrapper) {
        super(argument, wrapper);
    }

    @Override
    protected T evaluate(T first) {
        return wrapper.count(first);
    }
}
