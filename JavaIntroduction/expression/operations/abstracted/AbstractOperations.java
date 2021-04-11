package expression.operations.abstracted;

import expression.CommonExpression;
import expression.operations.Wrapper;

public abstract class AbstractOperations<T> implements CommonExpression<T> {
    private CommonExpression<T> rightOperand;
    private CommonExpression<T> leftOperand;
    protected Wrapper<T> wrapper;

    public AbstractOperations(CommonExpression<T> leftOperand, CommonExpression<T> rightOperand, Wrapper<T> wrapper) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.wrapper = wrapper;
    }

    @Override
    public T evaluate(String x, String y, String z) {
        T first = leftOperand.evaluate(x, y, z);
        T second = rightOperand.evaluate(x, y, z);
        return evaluate(first, second);
    }
    protected abstract T evaluate(T first, T second);
}