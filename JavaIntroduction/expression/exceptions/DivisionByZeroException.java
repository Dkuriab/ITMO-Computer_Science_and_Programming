package expression.exceptions;

public class DivisionByZeroException extends EvaluateException {
    public DivisionByZeroException() {
       super("DivisionByZeroException");
    }
}
