package sheep.expression.arithmetic;

import sheep.expression.Expression;

/**
 * A minus operation.
 */
class Minus extends Arithmetic {

    /**
     * Constructs a new minus expression.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length > 0.
     */
    public Minus(Expression[] arguments) {
        super("-", arguments);
    }

    /**
     * Perform a minus operation over the list of arguments.
     *
     * @param arguments A list of numbers to perform the operation upon.
     * @return The result of minus.
     */
    protected long perform(long[] arguments) {
        long number = 0L;
        for (int i = 0; i < arguments.length; i++) {
            if (i == 0) {
                number = arguments[0];
            } else {
                number = number - arguments[i];
            }
        }
        return number;
    }
}
