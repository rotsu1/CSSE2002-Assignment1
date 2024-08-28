package sheep.parsing;

import sheep.expression.Expression;
import sheep.expression.ExpressionFactory;
import sheep.expression.InvalidExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parser of basic expressions and arithmetic expressions.
 */
public class SimpleParser implements Parser {

    /**
     * A new instance of Parser.
     */
    private final ExpressionFactory factory;

    /**
     * Constructor
     * @param factory Factory used to construct parsed expressions.
     */
    public SimpleParser(ExpressionFactory factory) {
        this.factory = factory;
    }

    /**
     * Attempts to parse a string expression into an expression.
     * @param input A string to attempt to parse.
     *              If it contains an operator, it must have spaces on both sides.
     *              e.g. "4 + 5" is acceptable but "4+5" is not.
     * @return The result of parsing the expression.
     * @throws ParseException If the string input is not recognisable as an expression.
     */
    public Expression parse(String input) throws ParseException {
        input = input.trim();
        String operator;
        List<String> listExpression;
        if (input.isEmpty()) {
            return this.factory.createEmpty();
            // If the input is not an empty string
        } else {
            if (input.contains("=")) {
                operator = "=";
                String[] arrayExpression = input.split("=");
                listExpression = Arrays.asList(arrayExpression);
            } else if (input.contains("<")) {
                operator = "<";
                String[] arrayExpression = input.split("<");
                listExpression = Arrays.asList(arrayExpression);
            } else if (input.contains("+")) {
                operator = "+";
                listExpression = splitPlus(input);
            } else if (input.contains("-")) {
                operator = "-";
                String[] arrayExpression = input.split("-");
                listExpression = Arrays.asList(arrayExpression);
            } else if (input.contains("*")) {
                operator = "*";
                listExpression = splitTimes(input);
            } else if (input.contains("/")) {
                operator = "/";
                String[] arrayExpression = input.split("/");
                listExpression = Arrays.asList(arrayExpression);
            } else {
                return singleConverter(input);
            }
        }

        if (listExpression.size() <= 2) {
            try {
                return singleConverter(input);
            } catch (ParseException e) {
                operator = operator;
            }
        }

        List<Expression> expressions = new ArrayList<>();

        for (String expression : listExpression) {
            expression = expression.trim();
            expressions.add(parse(expression));
        }
        Object[] arrayOfExpressions = expressions.toArray();
        try {
            return this.factory.createOperator(operator, arrayOfExpressions);
        } catch (InvalidExpression e) {
            throw new ParseException("Cannot parse.");
        }
    }

    /**
     * Converts a string to a Constant or Reference.
     *
     * @param input The string to be parsed
     * @return A Constant expression or Reference expression.
     * @throws ParseException If it cannot be parsed.
     */
    private Expression singleConverter(String input) throws ParseException {
        try {
            input = input.trim();
            long constant = Long.parseLong(input);
            return this.factory.createConstant(constant);
        } catch (NumberFormatException e) {
            for (char character : input.toCharArray()) {
                if (!Character.isAlphabetic(character) && !Character.isDigit(character)) {
                    throw new ParseException("Not a number.");
                }
            }
            return this.factory.createReference(input);
        }
    }

    /**
     * Splits the string on +.
     *
     * @param input String attempt to parse.
     * @return A list of split strings.
     */
    private List<String> splitPlus(String input) {
        List<String> splitExpression = new ArrayList<>();
        String newString = input;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '+') {
                splitExpression.add(newString.substring(0, newString.indexOf("+")));
                newString = newString.substring(newString.indexOf("+") + 1);
            }
        }
        splitExpression.add(newString);
        return splitExpression;
    }

    /**
     * Splits the string on *.
     *
     * @param input String attempt to parse.
     * @return A list of split strings.
     */
    private List<String> splitTimes(String input) {
        List<String> splitExpression = new ArrayList<>();
        String newString = input;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '*') {
                splitExpression.add(newString.substring(0, newString.indexOf("*")));
                newString = newString.substring(newString.indexOf("*") + 1);
            }
        }
        splitExpression.add(newString);
        return splitExpression;
    }
}