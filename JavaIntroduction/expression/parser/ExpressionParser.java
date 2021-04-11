package expression.parser;

import expression.*;
import expression.operations.Wrapper;
import expression.operations.abstracted.*;
import expression.token.*;
import expression.exceptions.*;


public class ExpressionParser<T> implements Parser<T> {
    private Tokenizer tokens;
    private Wrapper<T> wrapper;
    private int openedParenthesis;

    public TripleExpression<T> parse(String expression, Wrapper<T> wrapper) {
        this.wrapper = wrapper;
        tokens = new Tokenizer(expression);

        Token token = tokens.showNext();
        if (tokens.length() == 2 && token.getType() != TokenType.CONST && token.getType() != TokenType.VARIABLE) {
            throw new DuringParseException("Bare " + token.getValue());
        } else if (tokens.length() == 1) {
            throw new DuringParseException("Empty expression");
        }
        return start(false);
    }

    private CommonExpression<T> start(boolean openBracketBefore) {
        CommonExpression<T> expression = fourthLevel();

        if (tokens.hasNext()) {
            Token token = tokens.next();

            switch (token.getType()) {
                case END:
                    if (openBracketBefore) {
                        throw new DuringParseException("No closing parenthesis for " + openedParenthesis);
                    }
                    return expression;

                case RIGHT_BR:
                    if (!openBracketBefore) {
                        throw new DuringParseException("No opening parenthesis for " + token.getPosition());
                    }
                    return expression;

                case LEFT_BR:
                    throw new DuringParseException("No closing parenthesis for " + openedParenthesis);

                default:
                    throw new DuringParseException("Spaces in numbers at " + token.getPosition());
            }
        }
        return expression;
    }

    private CommonExpression<T> fourthLevel() {
        CommonExpression<T> fourth = thirdLevel();

        while (tokens.hasNext()) {
            Token current = tokens.next();

            switch (current.getType()) {
                case MIN:
                    fourth = new Min<>(fourth, thirdLevel(), wrapper);
                    break;
                case MAX:
                    fourth = new Max<>(fourth, thirdLevel(), wrapper);
                    break;
                default:
                    tokens.prev();
                    return fourth;
            }
        }
        return fourth;
    }
    private CommonExpression<T> thirdLevel() {
        CommonExpression<T> third = secondLevel();

        while (tokens.hasNext()) {
            Token token = tokens.next();

            switch (token.getType()) {
                case PLUS:
                    third = new Add<>(third, secondLevel(), wrapper);
                    break;

                case MINUS:
                    third = new Subtract<>(third, secondLevel(), wrapper);
                    break;

                default:
                    tokens.prev();
                    return third;
            }
        }
        return third;
    }

    private CommonExpression<T> secondLevel() {
        CommonExpression<T> second = firstLevel();

        while (tokens.hasNext()) {
            Token token = tokens.next();

            switch (token.getType()) {
                case MUL:
                    second = new Multiply<>(second, firstLevel(), wrapper);
                    break;

                case DIV:
                    second = new Divide<>(second, firstLevel(), wrapper);
                    break;

                default:
                    tokens.prev();
                    return second;
            }
        }
        return second;
    }

    private CommonExpression<T> firstLevel() {
        Token token = tokens.next();
        CommonExpression<T> firstLevel = null;

        switch (token.getType()) {
            case END:
                throw new DuringParseException("No right argument for " + tokens.prev().getValue() + " at " + tokens.current().getPosition());

            case RIGHT_BR:
                if (tokens.showPrev().getType() == TokenType.LEFT_BR) {
                    throw new DuringParseException("() at " + tokens.showPrev().getPosition());
                }
                throw new DuringParseException("No right argument for " + tokens.prev().getValue() + " at " + tokens.current().getPosition());

            case MINUS:
                firstLevel = new Negate<>(firstLevel(), wrapper);
                break;

            case LEFT_BR:
                openedParenthesis = token.getPosition();
                firstLevel = start(true);
                if (tokens.current().getType() != TokenType.RIGHT_BR) {
                    throw new DuringParseException("No closing bracket for an opening at " + openedParenthesis);
                }
                break;

            case CONST:
                try {
                    firstLevel = new Const<>(token.getValue(), wrapper);
                } catch (NumberFormatException e) {
                    throw new DuringParseException("Constant overflow " + token.getValue());
                }
                break;

            case VARIABLE:
                firstLevel = new Variable<>(token.getValue(), wrapper);
                break;

            case COUNT:
                firstLevel = new Count<>(firstLevel(), wrapper);
                break;

            default:
                if (token.getPosition() == 0) {
                    throw new DuringParseException("No start argument");
                } else if (tokens.showPrev().getType() == TokenType.LEFT_BR) {
                    throw new DuringParseException("No left argument for " + tokens.current().getValue() + " at: " + tokens.current().getPosition());
                }
                throw new DuringParseException("No middle argument at " + token.getPosition());
        }
        return firstLevel;
    }
}