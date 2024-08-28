package sheep.expression.basic;

import sheep.expression.Expression;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A constant numeric value.
 */
public class Constant extends Expression {

    /**
     * A number to represent as an expression.
     */
    private final Long number;

    /**
     * Constructor
     *
     * @param number The number to represent as an expression.
     */
    public Constant(long number) {
        this.number = number;
    }

    /**
     * Get the numeric value stored within the constant expression.
     *
     * @return Value stored within the expression.
     */
    public long getValue() {
        return this.number;
    }

    /**
     * String representation of the constant. The result should be formatted as
     * "CONSTANT([number])"
     * e.g.
     *  Constant four = new Constant(4);
     *  four.toString(); // "CONSTANT(4)"
     *
     * @return String representation of the constant.
     */
    @Override
    public String toString() {
        return String.format("CONSTANT(%s)", this.number);
    }

    /**
     * Determine if two constants are equal.
     * Equal if they are both instances of constant and have the same value.
     *
     * @param obj Another object to compare against.
     * @return If the given object is equal to this object.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Constant constant) {
            return constant.number.equals(this.number);
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
        return number.hashCode() * 2;
    }

    /**
     * Dependencies of the constant expression. Constant expressions have no dependencies.
     *
     * @return An empty set to represent no dependencies.
     */
    public Set<String> dependencies() {
        return new HashSet<>();
    }

    /**
     * Return itself.
     *
     * @param state - A mapping of references to the expression they hold.
     * @return Itself.
     */
    public Expression value(Map<String, Expression> state) {
        return this;
    }

    /**
     * Return the numeric value stored.
     * e.g.
     *  Constant four = new Constant(4);
     *  four.value(); // 4
     *
     * @return A long that represents the numeric value of the expression.
     */
    public long value() {
        return this.getValue();
    }

    /**
     * The string representation of the numeric value.
     * e.g.
     *  Constant four = new Constant(4L);
     *  four.render(); // "4"
     *
     * @return the string representation of the expression.
     */
    public String render() {
        return String.format("%s", this.number);
    }
}
