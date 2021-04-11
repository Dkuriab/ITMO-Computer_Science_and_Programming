package expression.operations.abstracted;

import expression.CommonExpression;
import expression.operations.Wrapper;

public class Min<T> extends AbstractOperations<T> {

    public Min(CommonExpression<T> leftOperand, CommonExpression<T> rightOperand, Wrapper<T> wrapper) {
        super(leftOperand, rightOperand, wrapper);
    }

    @Override
    protected T evaluate(T first, T second) {
        return wrapper.min(first, second);
    }
}
