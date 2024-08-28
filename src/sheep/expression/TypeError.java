package sheep.expression;

/**
 * Thrown if an expression cannot be resolved into a numeric value.
 */
public class TypeError extends Exception {

    /**
     * Constructor without any additional details.
     */
    public TypeError() {}

    /**
     * Constructs a new exception with a description of the exception.
     *
     * @param message The description of the exception.
     */
    public TypeError(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with another exception as the base cause.
     *
     * @param base The exception that caused this exception to be thrown.
     */
    public TypeError(Exception base) {
        super(base);
    }
}
