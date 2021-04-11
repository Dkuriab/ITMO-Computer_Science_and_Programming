package expression.generic;

import expression.TripleExpression;
import expression.operations.IntegerOperation;
import expression.parser.ExpressionParser;
import expression.parser.Parser;

public class Tester {
    public static void main(String[] args) {
        Parser<Integer> test = new ExpressionParser<>();
        TripleExpression<Integer> exp = test.parse("-4", new IntegerOperation());
        System.out.println( exp.evaluate("0", "0" , "0"));
    }
}
