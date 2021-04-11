package expression.operations;

public interface Wrapper<T> {
    T divide(T a, T b);
    T multiply(T a, T b);
    T subtract(T a, T b);
    T parse(String value);
    T add(T a, T b);
    T negate(T a);
    T count(T first);
    T min(T a, T b);
    T max(T a, T b);
}
