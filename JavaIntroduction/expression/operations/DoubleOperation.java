package expression.operations;

public class DoubleOperation implements Wrapper<Double> {
     
    public Double divide(Double a, Double b) {
        return a / b;
    }

    public Double multiply(Double a, Double b) {
        return a * b;
    }

    public Double subtract(Double a, Double b) {
        return a - b;
    }
     
    public Double parse(String value) {
        return Double.valueOf(value);
    }
     
    public Double add(Double a, Double b) {
        return a + b;
    }
     
    public Double negate(Double a) {
        return -a;
    }

    public Double count(Double first) {
        return (double) Long.bitCount(Double.doubleToLongBits(first));
    }

    @Override
    public Double min(Double a, Double b) {
        return a < b ? a : b;
    }

    @Override
    public Double max(Double a, Double b) {
        return a > b ? a : b;
    }
}
