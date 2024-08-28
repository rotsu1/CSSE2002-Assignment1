package sheep.expression.arithmetic;

import sheep.expression.Expression;

/**
 * A division operation.
 */
class Divide extends Arithmetic {

    /**
     * Constructs a new division expression.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length > 0
     */
    public Divide(Expression[] arguments) {
        super("/", arguments);
    }

    /**
     * Perform integer division over the list of arguments.
     *
     * @param arguments A list of numbers to perform the operation upon.
     * @return The result of division.
     */
    protected long perform(long[] arguments) {
        long number = 0L;
        for (int i = 0; i < arguments.length; i++) {
            if (i == 0) {
                number = arguments[0];
            } else {
                number = number / arguments[i];
            }
        }
        return number;
    }
}
