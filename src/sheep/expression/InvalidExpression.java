package sheep.expression;

/**
 * Thrown if an error occur in the creation of an Expression in an ExpressionFactory.
 */
public class InvalidExpression extends Exception {

    /**
     * Constructor
     */
    public InvalidExpression() {

    }

    /**
     * Constructs a new exception with a description of the exception.
     * @param message The description of the exception.
     */
    public InvalidExpression(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with another exception as the base cause.
     * @param base Construct a new exception with another exception as the base cause.
     */
    public InvalidExpression(Exception base) {
        super(base);
    }
}
