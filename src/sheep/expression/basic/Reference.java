package sheep.expression.basic;

import sheep.expression.Expression;
import sheep.expression.TypeError;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A reference to a given identifier.
 */
public class Reference extends Expression {

    /**
     * An identifier of a cell or built-in.
     */
    private final String identifier;

    /**
     * Constructor
     *
     * @param identifier An identifier of a cell or a built-in.
     * @requires identifier != "", identifier != null.
     */
    public Reference(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Returns the identifier of the reference.
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * String representation of the reference.
     * The result should be formatted as "REFERENCE([identifier])".
     *
     * @return String representation of the expression.
     */
    @Override
    public String toString() {
        return String.format("REFERENCE(%s)", this.identifier);
    }

    /**
     * Returns whether the expression is a reference. For the reference expression, this must
     * return true.
     * @return true.
     */
    @Override
    public boolean isReference() {
        return true;
    }

    /**
     * If two instances of reference are equal to each other. Equality is defined by having the
     * same identifier.
     *
     * @param obj another instance to compare against.
     * @return true if the other object is a reference with the same identifier.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Reference reference) {
            return reference.identifier.equals(this.identifier);
        }
        return false;
    }

    /**
     * A hashcode method that respects the equals(Object) method.
     *
     * @return An appropriate hashcode value for this instance.
     */
    @Override
    public int hashCode() {
        return identifier.hashCode() * 4;
    }

    /**
     * Dependencies of the reference expression. The dependencies of a reference are its
     * identifier.
     *
     * @return A set containing the references' identifier.
     */
    public Set<String> dependencies() {
        Set<String> dependencies = new HashSet<>();
        dependencies.add(this.identifier);
        return dependencies;
    }

    /**
     * If the given state does not have an entry for this reference's identifier, return this.
     * Otherwise, return the result of calling Expression.value(Map) on the entry in the state.
     *
     * @param state - A mapping of references to the expression they hold.
     * @return The result of evaluating this expression.
     * @throws TypeError - If a type error occurs in the process of evaluation.
     */
    public Expression value(Map<String, Expression> state) throws TypeError {
        if (state.containsKey(this.identifier)) {
            return state.get(this.identifier).value(state);
        }
        return this;
    }

    /**
     * Always throws type error.
     *
     * @return Nothing will be returned as a TypeError is always thrown.
     * @throws TypeError Will always be thrown by Reference.
     */
    public long value() throws TypeError {
        throw new TypeError();
    }

    /**
     * The string representation of the expression. For references, this is the referenced
     * identifier.
     * e.g.
     * Reference hello = new Reference("hello");
     * hello.render(); // "hello"
     *
     * @return the string representation of the expression.
     */
    public String render() {
        return String.format("%s", this.identifier);
    }
}
