package expression.token;

import expression.exceptions.DuringParseException;
import expression.exceptions.InvalidSymbolException;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private List<Token> tokens = new ArrayList<>();
    private int curr = -1;

    public Tokenizer(String expression) {
        tokenize(expression);
        tokens.add(new Token(TokenType.END, "end of expression", expression.length()));
    }

    public int length() {
        return tokens.size();
    }

    public boolean hasNext() {
        return curr < tokens.size() - 1;
    }

    public Token next() {
        return tokens.get(++curr);
    }

    public Token prev() {
        return tokens.get(--curr);
    }

    public Token showPrev() {
        curr--;
        return tokens.get(curr++);
    }

    public Token showNext() {
        curr++;
        return tokens.get(curr--);
    }

    public Token current() {
        return tokens.get(curr);
    }

    private void tokenize(String s) {
        int i = -1;
        while(i < s.length() - 1) {
            i++;
            if (Character.isWhitespace(s.charAt(i))) {
                continue;
            }
            switch (s.charAt(i)) {
                case '(':
                    tokens.add(new Token(TokenType.LEFT_BR, "(", i));
                    break;

                case ')':
                    tokens.add(new Token(TokenType.RIGHT_BR, ")", i));
                    break;

                case '+':
                    tokens.add(new Token(TokenType.PLUS, "+", i));
                    break;

                case '-':
                    tokens.add(new Token(TokenType.MINUS, "-", i));
                    break;

                case '*':
                    tokens.add(new Token(TokenType.MUL, "*", i));
                    break;

                case '/':
                    tokens.add(new Token(TokenType.DIV, "/", i));
                    break;

                case 'c':
                    if (!check(s, i, "count")) {
                        throw new InvalidSymbolException(i);
                    }
                    tokens.add(new Token(TokenType.COUNT, "COUNT", i));
                    i += 4;
                    break;

                case 'm':
                    if (check(s, i, "min")) {
                        tokens.add(new Token(TokenType.MIN, "MIN", i));
                    } else if (check(s, i, "max")) {
                        tokens.add(new Token(TokenType.MAX, "MAX", i));
                    } else {
                        throw new InvalidSymbolException(i);
                    }
                    i += 2;
                    break;

                case 'x':
                case 'y':
                case 'z':
                    tokens.add(new Token(TokenType.VARIABLE, String.valueOf(s.charAt(i)), i));
                    break;

                default:
                    if (!Character.isDigit(s.charAt(i))) {
                        throw new InvalidSymbolException(i);
                    }
                    int j = i;
                    while (j < s.length() && (Character.isDigit(s.charAt(j)) || s.charAt(j) == '.')) {
                        j++;
                    }

                    tokens.add(new Token(TokenType.CONST, s.substring(i, j), i));
                    i = j - 1;
                    break;
            }
        }
    }

    private boolean check(String s, int i, String checking) {
        for (char c: checking.toCharArray()) {
            if (s.charAt(i++) != c) {
                return false;
            }
        }
        return true;
    }
}