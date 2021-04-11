package expression.operations;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class ShortOperation implements Wrapper<Short> {
    @Override
    public Short divide(Short a, Short b) {
        if (b == 0) {
            throw new DivisionByZeroException();
        } else if (a == Short.MIN_VALUE && b == -1) {
            throw new OverflowException("Overflow: " + a + "/" + b);
        } else {
            return (short) (a / b);
        }
    }

    @Override
    public Short multiply(Short a, Short b) {
        return (short) (a * b);
    }

    @Override
    public Short subtract(Short a, Short b) {
        return (short) (a - b);
    }

    @Override
    public Short parse(String value) {
        return (short) Integer.parseInt(value);
    }

    @Override
    public Short add(Short a, Short b) {
        return (short) (a + b);
    }

    @Override
    public Short negate(Short a) {
        return (short) (-a);
    }

    @Override
    public Short count(Short first) {
        return (short) Integer.bitCount(first.intValue());
    }

    @Override
    public Short min(Short a, Short b) {
        return a < b ? a : b;
    }

    @Override
    public Short max(Short a, Short b) {
        return a > b ? a : b;
    }
}
