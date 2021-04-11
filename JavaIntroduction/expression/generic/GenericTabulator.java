package expression.generic;

import expression.TripleExpression;
import expression.exceptions.EvaluateException;
import expression.operations.*;
import expression.parser.ExpressionParser;

import java.math.BigInteger;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] answer = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];

        TripleExpression<Integer> parsedInteger = (new ExpressionParser<Integer>()).parse(expression, new IntegerOperation());

        TripleExpression<Double> parsedDouble = (new ExpressionParser<Double>()).parse(expression, new DoubleOperation());

        TripleExpression<BigInteger> parsedBigInteger = (new ExpressionParser<BigInteger>()).parse(expression, new BigIntegerOperation());

        TripleExpression<Short> parsedShort = (new ExpressionParser<Short>()).parse(expression, new ShortOperation());

        TripleExpression<Long> parsedLong = (new ExpressionParser<Long>()).parse(expression, new LongOperation());


        for (int z = z1; z <= z2; z++) {
            for (int y = y1; y <= y2; y++) {
                for (int x = x1; x <= x2; x++) {
                    try {
                        switch (mode.charAt(0)) {
                            case 'i':
                                answer[x - x1][y - y1][z - z1] = parsedInteger.evaluate(Integer.toString(x), Integer.toString(y), Integer.toString(z));
                                break;
                            case 'd':
                                answer[x - x1][y - y1][z - z1] = parsedDouble.evaluate(Integer.toString(x), Integer.toString(y), Integer.toString(z));
                                break;
                            case 'b':
                                answer[x - x1][y - y1][z - z1] = parsedBigInteger.evaluate(Integer.toString(x), Integer.toString(y), Integer.toString(z));
                                break;
                            case 'l':
                                answer[x - x1][y - y1][z - z1] = parsedLong.evaluate(Integer.toString(x), Integer.toString(y), Integer.toString(z));
                                break;
                            case 's':
                                answer[x - x1][y - y1][z - z1] = parsedShort.evaluate(Integer.toString(x), Integer.toString(y), Integer.toString(z));
                                break;
                        }

                    } catch (EvaluateException e) {
                        answer[x - x1][y - y1][z - z1] = null;
                    }
                }
            }
        }


        return answer;
    }
}
