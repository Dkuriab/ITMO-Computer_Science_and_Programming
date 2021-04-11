package expression.operations;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class IntegerOperation implements Wrapper<Integer> {

    public Integer divide(Integer a, Integer b) {
        if (b == 0) {
            throw new DivisionByZeroException();
        } else if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("Overflow: " + a + "/" + b);
        } else {
            return a / b;
        }
    }

    public Integer multiply(Integer a, Integer b) {
        if ((a > 0 && b > 0 && a > Integer.MAX_VALUE / b)
                || (a < 0 && b < 0 && a < Integer.MAX_VALUE / (b + .0))
                || (a > 0 && b < 0 && a > Integer.MIN_VALUE / (b + .0))
                || (a < 0 && b > 0 && a < Integer.MIN_VALUE / (b + .0))) {
            throw new OverflowException("overflow: " + a + "*" + b);
        } else {
            return (a * b);
        }
    }

    public Integer subtract(Integer a, Integer b) {
        if ((a >= 0 && b < 0 && a > Integer.MAX_VALUE + b)
                || (a < 0 && b > 0 && a < Integer.MIN_VALUE + b)) {
            throw new OverflowException("overflow: " + a + "-" + b);
        } else {
            return (a - b);
        }
    }

    public Integer parse(String value) {
        return Integer.valueOf(value);
    }

    public Integer add(Integer a, Integer b) throws OverflowException {
        if ((a > 0 && b > 0 && a > Integer.MAX_VALUE - b)
                || (a < 0 && b < 0 && a < Integer.MIN_VALUE - b)) {
            throw new OverflowException("overflow: " + a + "+" + b);
        } else {
            return (a + b);
        }
    }

    public Integer negate(Integer a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("overflow: - (" + a + ")");
        } else {
            return -a;
        }
    }

    public Integer count(Integer first) {
        return Integer.bitCount(first);
    }

    @Override
    public Integer min(Integer a, Integer b) {
        return a < b ? a : b;
    }

    @Override
    public Integer max(Integer a, Integer b) {
        return a > b ? a : b;
    }
}
