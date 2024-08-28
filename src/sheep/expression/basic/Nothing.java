package sheep.expression.basic;

import sheep.expression.Expression;
import sheep.expression.TypeError;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An empty expression.
 */
public class Nothing extends Expression {

    /**
     * Dependencies of the empty expression. Empty expressions have no dependencies.
     *
     * @return An empty set to represent no dependencies.
     */
    @Override
    public Set<String> dependencies() {
        return new HashSet<>();
    }

    /**
     * Return itself
     *
     * @param state A mapping of references to the expression they hold.
     * @return Itself.
     * @throws TypeError If a type error occurs in the process of evaluation.
     */
    public Expression value(Map<String, Expression> state) throws TypeError {
        return this;
    }

    /**
     * Type error is always thrown.
     *
     * @return Nothing will be returned as a TypeError is always thrown.
     * @throws TypeError Will always be thrown by Nothing.
     */
    public long value() throws TypeError {
        throw new TypeError();
    }

    /**
     * The string representation of the expression. For empty expressions, this is the empty
     * string.
     *
     * @return the string representation of the expression.
     */
    public String render() {
        return "";
    }

    /**
     * String representation of the empty expression.
     *
     * @return String representation of the expression.
     */
    public String toString() {
        return "NOTHING";
    }
}