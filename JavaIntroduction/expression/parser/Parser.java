package expression.parser;

import expression.TripleExpression;
import expression.operations.Wrapper;

public interface Parser<T> {
    TripleExpression<T> parse(String expression, Wrapper<T> wrapper);
}
