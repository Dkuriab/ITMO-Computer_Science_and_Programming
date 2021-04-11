package expression.operations;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class LongOperation implements Wrapper<Long> {
    @Override
    public Long divide(Long a, Long b) {
        if (b == 0) {
            throw new DivisionByZeroException();
        } else if (a == Long.MIN_VALUE && b == -1) {
            throw new OverflowException("Overflow: " + a + "/" + b);
        } else {
            return a / b;
        }
    }

    @Override
    public Long multiply(Long a, Long b) {
        return a * b;
    }

    @Override
    public Long subtract(Long a, Long b) {
        return a - b;
    }

    @Override
    public Long parse(String value) {
        return Long.parseLong(value);
    }

    @Override
    public Long add(Long a, Long b) {
        return a + b;
    }

    @Override
    public Long negate(Long a) {
        return -a;
    }

    @Override
    public Long count(Long first) {
        return (long) Long.bitCount(first);
    }

    @Override
    public Long min(Long a, Long b) {
        return a < b ? a : b;
    }

    @Override
    public Long max(Long a, Long b) {
        return a > b ? a : b;
    }
}
