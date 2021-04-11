package expression;

public interface CommonExpression<T> extends TripleExpression<T> {
	T evaluate(String x, String  y, String z);
}