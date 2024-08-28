package sheep.expression.arithmetic;

import sheep.expression.Expression;

/**
 * A times operation.
 */
class Times extends Arithmetic {

    /**
     * Constructs a new times expression.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length > 0.
     */
    public Times(Expression[] arguments) {
        super("*", arguments);
    }

    protected long perform(long[] arguments) {
        long number = 0L;
        for (int i = 0; i < arguments.length; i++) {
            if (i == 0) {
                number = arguments[0];
            } else {
                number = number * arguments[i];
            }
        }
        return number;
    }
}
