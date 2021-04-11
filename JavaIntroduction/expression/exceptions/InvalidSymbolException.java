package expression.exceptions;

public class InvalidSymbolException extends ArithmeticException {
    public InvalidSymbolException(int position) {
        super("Invalid symbol at: " + position);
    }
}
