package expression.operations.abstracted;

import expression.CommonExpression;
import expression.operations.Wrapper;

public class Subtract<T> extends AbstractOperations<T> {

    public Subtract(CommonExpression<T> leftOperand, CommonExpression<T> rightOperand, Wrapper<T> wrapper) {
        super(leftOperand, rightOperand, wrapper);
    }

    @Override
    public T evaluate(T first, T second) {
        return wrapper.subtract(first, second);
    }
}