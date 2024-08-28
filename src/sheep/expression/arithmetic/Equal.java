package sheep.expression.arithmetic;

import sheep.expression.Expression;

/**
 * An equal operation.
 */
class Equal extends Arithmetic {

    /**
     * Construct a new equal to expression.
     *
     * @param arguments arguments - A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length > 0.
     */
    public Equal(Expression[] arguments) {
        super("=", arguments);
    }

    /**
     * Perform an equal to operation over the list of arguments.
     * This method will return 1 if all arguments are equal, 0 otherwise.
     *
     * @param arguments A list of numbers to perform the operation upon.
     * @return 1 is all arguments are equal, 0 otherwise.
     */
    protected long perform(long[] arguments) {
        long number = 0L;
        for (int i = 0; i < arguments.length; i++) {
            if (i == 0) {
                number = arguments[0];
            } else {
                if (number != arguments[i]) {
                    return 0;
                }
            }
        }
        return 1;
    }
}
