package sheep.parsing;

/**
 * Thrown if an expression cannot be parsed.
 */
public class ParseException extends Exception {

    /**
     * Constructor with without additional details.
     */
    public ParseException() {}

    /**
     * Constructor with a description.
     *
     * @param message The description of the exception.
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Constructor with another exception as the base clause.
     *
     * @param base The exception that caused this exception to be thrown.
     */
    public ParseException(Exception base) {
        super(base);
    }
}
