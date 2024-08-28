package sheep.expression.arithmetic;

import sheep.expression.Expression;

/**
 * A less than operation.
 */
class Less extends Arithmetic {

    /**
     * Constructs a new less than expression.
     *
     * @param arguments - A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length > 0.
     */
    public Less(Expression[] arguments) {
        super("<", arguments);
    }

    /**
     * Perform a less than operation over the list of arguments.
     * This method will return 1 if all arguments are in increasing ordered, 0 otherwise.
     *
     * @param arguments A list of numbers to perform the operation upon.
     * @return 1 if all arguments are in increasing ordered, 0 otherwise.
     */
    protected long perform(long[] arguments) {
        long number = 0L;
        for (int i = 0; i < arguments.length; i++) {
            if (i == 0) {
                number = arguments[0];
            } else {
                if (number >= arguments[i]) {
                    return 0L;
                }
                number = arguments[i];
            }
        }
        return 1L;
    }
}
