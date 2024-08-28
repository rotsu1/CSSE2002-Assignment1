package sheep.expression;

import sheep.expression.arithmetic.*;
import sheep.expression.basic.Constant;
import sheep.expression.basic.Nothing;
import sheep.expression.basic.Reference;

/**
 * An expression factory for the core expressions.
 */
public class CoreFactory implements ExpressionFactory {

    /**
     * Constructor without any additional details.
     */
    public CoreFactory() {}

    /**
     * Creates an instance of Reference that stores the given identifier.
     *
     * @param identifier A reference to either a cell or a built-in.
     * @return An instance of Reference that stores the given identifier.
     * @requires identifier != "".
     **/
    public Expression createReference(String identifier) {
        return new Reference(identifier);
    }

    /**
     * Creates an instance of Constant that stores the given value.
     *
     * @param value A constant long value of the expression.
     * @return An instance of Constant that stores the given value.
     */
    public Expression createConstant(long value) {
        return new Constant(value);
    }

    /**
     * Creates an instance of Nothing.
     *
     * @return An instance of Nothing.
     */
    public Expression createEmpty() {
        return new Nothing();
    }

    /**
     * Creates an instance of Arithmetic based on the given operator name.
     * This method should handle operator names of;
     * +, -, *, /, <, =;
     * and create the appropriate Arithmetic subclass.
     * Else, InvalidExpression should be thrown.
     *
     * @param name An identifier for the operator, e.g. +, *.
     * @param args  A list of Expression instances as arguments to the Arithmetic instance.
     * @return An appropriate operator expression.
     * @throws InvalidExpression If the operator name is unknown or if any of the given Object
     * arguments are not subclasses of Expression or if there are no arguments given.
     */
    public Expression createOperator(String name, Object[] args) throws InvalidExpression {
        Expression[] expressions = new Expression[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Expression) {
                expressions[i] = (Expression) args[i];
            } else {
                throw new InvalidExpression("This is an invalid expression");
            }
        }
        if (expressions.length > 0) {
            switch (name) {
                case "+" -> {
                    return Arithmetic.plus(expressions);
                }
                case "-" -> {
                    return Arithmetic.minus(expressions);
                }
                case "*" -> {
                    return Arithmetic.times(expressions);
                }
                case "/" -> {
                    return Arithmetic.divide(expressions);
                }
                case "<" -> {
                    return Arithmetic.less(expressions);
                }
                case "=" -> {
                    return Arithmetic.equal(expressions);
                }
                default -> throw new InvalidExpression("This is an invalid expression.");
            }
        }
        throw new InvalidExpression("It must contain at least one expression.");
    }
}