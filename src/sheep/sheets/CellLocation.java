package sheep.sheets;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A location of a cell within a grid.
 * This class represents a location via a row, column coordinate system.
 */
public class CellLocation {

    /**
     * Row of table.
     */
    private final Integer row;

    /**
     * Column of table.
     */
    private final Integer column;

    private static final String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Constructs a new cell location at the given row and column.
     *
     * @param row A number representing the row number.
     * @param column A character representing the column.
     * @requires row is greater than or equal to zero, column is between 'A' and 'Z' inclusive.
     */
    public CellLocation(int row, char column) {
        Map<Character, Integer> map = new HashMap<>();
        int length = alphabets.length();
        for (int i = 0; i < length; i++) {
            map.put(alphabets.charAt(i), i);
        }
        this.row = row;
        this.column = map.get(column);
    }

    /**
     * Constructs a new cell location at the given row and column.
     *
     * @param row A number representing the row number.
     * @param column A number representing the column.
     * @requires row and column are greater than or equal to zero., column is less than 26.
     */
    public CellLocation(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Attempt to parse a string as a reference to a cell location.
     * If the string is not a reference to a cell location, returns Optional.empty().
     * The format of the reference is a single uppercase character followed by an integer without
     * spaces and without extraneous characters after the integer or before the character.
     *
     * @param ref A string that may represent a cell location.
     * @return An optional containing a cell reference if the string is a reference, otherwise the
     * empty optional.
     * @requires ref != null
     */
    public static Optional<CellLocation> maybeReference(String ref) {
        String number = "";
        for (char character : alphabets.toCharArray()) {
            // Check if the starting letter is an uppercase letter.
            if (character == ref.charAt(0)) {
                // Check if the other characters in ref are all integers.
                for (int i = 1; i < ref.length(); i++) {
                    if (Character.isDigit(ref.charAt(i))) {
                        number = number + ref.charAt(i);
                    } else {
                        return Optional.empty();
                    }
                }
                return Optional.of(new CellLocation(Integer.parseInt(number), ref.charAt(0)));
            }
        }
        return Optional.empty();
    }

    /**
     * The row number of this cell location.
     *
     * @return The row number of this cell location.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * The column number of this cell location.
     *
     * @return The column number of this cell location.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Returns true if two instances of cell location are equal to each other.
     * Equality is defined by having the same row and column.
     *
     * @param obj another instance to compare against.
     * @return true if the other object is a cell location with the same row number and column
     * number as the current cell location.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CellLocation cell) {
            return (this.row.equals(cell.row)
                    && this.column.equals((cell.column)));
        }
        return false;
    }

    /**
     * Returns a hashcode method that respects the equals(Object) method.
     *
     * @return An appropriate hashcode value for this instance.
     */
    @Override
    public int hashCode() {
        return row.hashCode() * column.hashCode() * 17;
    }

    /**
     * Returns a string representation of a cell location.
     *
     * @return A string representation of this cell location, e.g. A2 or C23.
     */
    @Override
    public String toString() {
        Map<Integer, Character> map = new HashMap<>();
        int length = alphabets.length();
        for (int i = 0; i < length; i++) {
            map.put(i, alphabets.charAt(i));
        }
        return String.format("%s%d", map.get(this.column), this.row);
    }
}