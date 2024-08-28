package sheep.expression.arithmetic;

import sheep.expression.Expression;

/**
 * A plus operation
 */
class Plus extends Arithmetic {

    /**
     * Constructs a new plus expression.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length > 0.
     */
    public Plus(Expression[] arguments) {
        super("+", arguments);
    }

    /**
     * Perform a plus operation over the list of arguments.
     *
     * @param arguments A list of numbers to perform the operation upon.
     * @return The result of plus.
     */
    protected long perform(long[] arguments) {
        long number = 0L;
        for (int i = 0; i < arguments.length; i++) {
            if (i == 0) {
                number = arguments[0];
            } else {
                number = number + arguments[i];
            }
        }
        return number;
    }
}
