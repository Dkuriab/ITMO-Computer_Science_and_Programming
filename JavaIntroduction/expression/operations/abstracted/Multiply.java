package expression.operations.abstracted;

import expression.CommonExpression;
import expression.operations.Wrapper;

public class Multiply<T> extends AbstractOperations<T> {

    public Multiply(CommonExpression<T> leftOperand, CommonExpression<T> rightOperand, Wrapper<T> wrapper) {
        super(leftOperand, rightOperand, wrapper);
    }

    protected T evaluate(T first, T second) {
        return wrapper.multiply(first, second);
    }

}
