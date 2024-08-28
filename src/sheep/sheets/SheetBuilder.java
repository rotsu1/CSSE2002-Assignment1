package sheep.sheets;

import sheep.expression.Expression;
import sheep.parsing.Parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder pattern to construct Sheet instances.
 */
public class SheetBuilder {

    /**
     * A new instance of parser.
     */
    private final Parser parser;

    /**
     * A new instance of Expression.
     */
    private final Expression defaultExpression;

    /**
     * Maps Expression to the identifier.
     */
    private final Map<String, Expression> identifierExpression = new HashMap<>();

    /**
     * Constructor.
     * @param parser Factory used to construct parsed expressions.
     * @param defaultExpression Expression
     */
    public SheetBuilder(Parser parser, Expression defaultExpression) {
        this.parser = parser;
        this.defaultExpression = defaultExpression;
    }

    /**
     * Include a new built-in expression for the given identifier within any sheet constructed
     * by this builder instance.
     *
     * @param identifier A string identifier to be used in the constructed sheet.
     * @param expression The value that the identifier should resolve to within the constructed
     *                  sheet.
     * @return SheetBuilder.
     * @requires identifier cannot be a valid cell location reference, e.g. A1.
     */
    public SheetBuilder includeBuiltIn(String identifier, Expression expression) {
        identifierExpression.put(identifier, expression);
        return new SheetBuilder(this.parser, defaultExpression);
    }

    /**
     * Constructs a new empty sheet with the given number of rows and columns.
     * If the built-ins are updated (i.e. includeBuiltIn(String, Expression) is called), this will
     * not affect the constructed sheet.
     *
     * @param rows - Amount of rows for the new sheet.
     * @param columns - Amount of columns for the sheet.
     * @return A new sheet with the appropriate built-ins and of the specified dimensions.
     */
    public Sheet empty(int rows, int columns) {
        return new Sheet(this.parser, identifierExpression,
                defaultExpression, rows, columns);
    }
}
