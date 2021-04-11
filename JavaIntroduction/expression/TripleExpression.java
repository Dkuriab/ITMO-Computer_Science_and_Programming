package expression;

import java.text.NumberFormat;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpression<T> extends ToMiniString {
    T evaluate(String x, String y, String z);
}
