package sheep.expression.arithmetic;

import sheep.expression.Expression;
import sheep.expression.TypeError;
import sheep.expression.basic.Constant;

import java.util.*;

/**
 * An arithmetic expression.
 * Performs arithmetic operations on a sequence of sub-expressions.
 */
public abstract class Arithmetic extends Expression {

    /**
     * Operator passed in by the constructor.
     */
    private final String operator;

    /**
     *  A sequence of sub-expressions to perform the operation upon.
     */
    private final Expression[] arguments;

    /**
     * Constructor
     *
     * @param operator The name of the arithmetic operation, e.g. plus.
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length > 0.
     */
    protected Arithmetic(String operator, Expression[] arguments) {

        this.operator = operator;
        this.arguments = arguments;
    }

    /**
     * Constructs a new addition (plus) operation.
     *
     * @param arguments A plus expression.
     * @return arguments.length > 0
     * @requires arguments.length > 0
     */
    public static Arithmetic plus(Expression[] arguments) {
        return new Plus(arguments);
    }

    /**
     * Constructs a new subtraction (minus) operation.
     *
     * @return A minus expression.
     * @requires arguments.length > 0
     */
    public static Arithmetic minus(Expression[] arguments) {
        return new Minus(arguments);
    }

    /**
     * Constructs a new multiplication (times) operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A times expression.
     * @requires arguments.length > 0
     */
    public static Arithmetic times(Expression[] arguments) {
        return new Times(arguments);
    }

    /**
     * Constructs a new division (divide) operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A divide expression.
     * @requires arguments.length > 0
     */
    public static Arithmetic divide(Expression[] arguments) {
        return new Divide(arguments);
    }

    /**
     * Constructs a new less than (less) operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A less expression.
     * @requires arguments.length > 0
     */
    public static Arithmetic less(Expression[] arguments) {
        return new Less(arguments);
    }

    /**
     * Constructs a new equal to (equal) operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return An equal expression.
     * @requires arguments.length > 0
     */
    public static Arithmetic equal(Expression[] arguments) {
        return new Equal(arguments);
    }

    /**
     * Dependencies of the arithmetic expression.
     * The dependencies of an arithmetic expression are the union of all sub-expressions.
     *
     * @return A set containing the union of all sub-expression dependencies.
     */
    public Set<String> dependencies() {
        Set<String> subDependency = new HashSet<>();
        for (Expression expression : this.arguments) {
            subDependency.addAll(expression.dependencies());
        }
        return subDependency;
    }

    /**
     * Result of evaluating this expression.
     *
     * @param state - A mapping of references to the expression they hold.
     * @return A constant expression of the result.
     * @throws TypeError If any of the sub-expressions cannot be converted to a numeric value.
     */
    public Expression value(Map<String, Expression> state) throws TypeError {
        long[] numbers = new long[this.arguments.length];
        for (int i = 0; i < this.arguments.length; i++) {
            try {
                numbers[i] = this.arguments[i].value(state).value();
            } catch (TypeError e) {
                throw new TypeError("Cannot convert to numeric value");
            }
        }
        return switch (this.operator) {
            case "+" -> new Constant(plus(this.arguments).perform(numbers));
            case "-" -> new Constant(minus(this.arguments).perform(numbers));
            case "*" -> new Constant(times(this.arguments).perform(numbers));
            case "<" -> new Constant(less(this.arguments).perform(numbers));
            case "=" -> new Constant(equal(this.arguments).perform(numbers));
            case "/" -> new Constant(divide(this.arguments).perform(numbers));
            default -> new Constant(perform(numbers));
        };
    }


    /**
     * Perform the arithmetic operation over a list of numbers.
     *
     * @param arguments A list of numbers to perform the operation upon.
     * @return The result of performing the arithmetic operation.
     */
    protected abstract long perform(long[] arguments);

    /**
     * Evaluate the expression to a numeric value.
     * For arithmetic expressions, a type error will always be thrown.
     *
     * @return Nothing will be returned as a TypeError is always thrown.
     * @throws TypeError Will always be thrown by Arithmetic.
     */
    public long value() throws TypeError {
        throw new TypeError();
    }

    /**
     * The string representation of the expression.
     * For arithmetic, this is the sequence of sub-expressions joined by the operator node.
     *
     * @return the string representation of the expression.
     */
    public String render() {
        String expression = "";
        for (int i = 0; i < this.arguments.length; i++) {
            if (i == 0) {
                expression = this.arguments[0].render();
            } else {
                expression = "%s %s %s".formatted(expression, operator, this.arguments[i].render());
            }
        }
        return expression;
    }

    /**
     * The string representation of the expression. For arithmetic, this is the sequence of
     * sub-expressions joined by the operator node.
     *
     * @return the string representation of the expression.
     */
    public String toString() {
        return this.render();
    }
}
