package expression.operations.abstracted;

import expression.CommonExpression;
import expression.operations.Wrapper;

public class Variable<T> implements CommonExpression<T> {
    private String name;
    private Wrapper<T> wrapper;

    public Variable(String name, Wrapper<T> wrapper) {
        this.name = name;
        this.wrapper = wrapper;
    }

    public T evaluate(String x, String y, String z) {
        switch (name) {
            case "x":
                return wrapper.parse(x);
            case "y":
                return wrapper.parse(y);
            case "z":
                return wrapper.parse(z);
            default:
                return null;
        }
    }
}