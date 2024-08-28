package sheep.expression;

import java.util.Map;
import java.util.Set;

/**
 * Value stored within spreadsheet cells.
 */
public abstract class Expression {

    /**
     * The set of references depended upon by the expression.
     * Implementations must be transitive, that is, if an expression has subexpressions, the
     * dependencies of all subexpressions must be included.
     * e.g.
     *  Expression expr = Arithmetic.plus(Arithmetic.minus(new Reference("A1"),
     *                                       new Reference("A2")), new Reference("B2"));
     *  expr.dependencies() // {"A1", "A2", "B2"}
     *
     * @return A set containing all the transitive references depended upon by the expression.
     */
    public abstract Set<String> dependencies();

    /**
     * Evaluate the expression to a numeric value.
     *
     * @return A long that represents the numeric value of the expression.
     * @throws TypeError If the method is called on an expression that does not have a numeric
     * value, e.g. Reference or Nothing.
     */
    public abstract long value() throws TypeError;

    /**
     * Calling this function must not alter the current expression.
     * If the current expression cannot evaluate further, return itself.
     *
     * @param state - A mapping of references to the expression they hold.
     * @return Either the expression itself or a new expression resulting from evaluation.
     * @throws TypeError TypeError - If a type error occurs in the process of evaluation.
     */
    public abstract Expression value(Map<String, Expression> state) throws TypeError;

    /**
     * True if the expression is a reference. The abstract class, expression, should return false by
     * default. The method will be overwritten by subclasses that are references.
     *
     * @return true if the expression is a reference.
     */
    public boolean isReference() {
        return false;
    }

    /**
     * The string representation of the expression.
     *
     * @return the string representation of the expression.
     */
    public abstract String render();
}
