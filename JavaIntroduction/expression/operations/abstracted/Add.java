package expression.operations.abstracted;

import expression.CommonExpression;
import expression.exceptions.OverflowException;
import expression.operations.Wrapper;

public class Add<T> extends AbstractOperations<T> {

    public Add(CommonExpression<T> leftOperand, CommonExpression<T> rightOperand, Wrapper<T> wrapper) {
        super(leftOperand, rightOperand, wrapper);
    }

    @Override
    protected T evaluate(T first, T second) throws OverflowException {
        return wrapper.add(first, second);
    }
}
