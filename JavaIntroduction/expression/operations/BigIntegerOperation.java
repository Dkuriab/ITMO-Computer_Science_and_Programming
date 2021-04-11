package expression.operations;

import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;

public class BigIntegerOperation implements Wrapper<BigInteger> {
    public BigInteger divide(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException();
        } else {
            return a.divide(b);
        }
    }

    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    public BigInteger parse(String value) {
        return new BigInteger(value);
    }

    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }
     
    public BigInteger negate(BigInteger a) {
        return a.negate();
    }

    public BigInteger count(BigInteger first) {
        return new BigInteger(String.valueOf(first.bitCount()));
    }

    @Override
    public BigInteger min(BigInteger a, BigInteger b) {
        return a.min(b);
    }

    @Override
    public BigInteger max(BigInteger a, BigInteger b) {
        return a.max(b);
    }
}
