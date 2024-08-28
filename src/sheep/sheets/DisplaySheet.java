package sheep.sheets;

import sheep.core.SheetView;
import sheep.core.SheetUpdate;
import sheep.core.UpdateResponse;
import sheep.core.ViewElement;
import sheep.expression.Expression;
import sheep.parsing.ParseException;
import sheep.parsing.Parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Spreadsheet that displays the expressions it holds without evaluating the expressions.
 */
public class DisplaySheet implements SheetUpdate, SheetView {
    private final Parser parser;
    private final int row;
    private final int columns;
    private final Expression defaultExpression;
    private final Map<String, Expression> rowColumnInput = new HashMap<>();

    /**
     * Constructor
     *
     * @param parser A parser to use for parsing any updates to the sheet.
     * @param defaultExpression The default expression with which to populate the empty sheet.
     * @param row Amount of rows for the new sheet.
     * @param columns Amount of columns for the new sheet.
     */
    public DisplaySheet(Parser parser, Expression defaultExpression, int row, int columns) {
        this.parser = parser;
        this.row = row;
        this.columns = columns;
        this.defaultExpression = defaultExpression;
    }

    /**
     * Attempt to update a cell in the position.
     *
     * @param row The row index to update.
     * @param column The column index to update.
     * @param input The value as a string to replace within the sheet.
     * @return Whether the update was successful or not with error details.
     * @requires 0 ≤ row < getRows(), 0 ≤ column < getColumns().
     */
    public UpdateResponse update(int row, int column, String input) {
        String rowColumn;
        try {
            Expression expression = this.parser.parse(input);
            rowColumn = Integer.toString(row) + ',' + Integer.toString(column);
            rowColumnInput.put(rowColumn, expression);
            return UpdateResponse.success();
        } catch (ParseException e) {
            return UpdateResponse.fail(String.format("Unable to parse: %s", input));
        }
    }

    /**
     * gets the number of rows in the sheet.
     *
     * @return Amount of rows in the sheet.
     */
    public int getRows() {
        return this.row;
    }

    /**
     * gets the number of columns in the sheet.
     *
     * @return Amount of columns in the sheet.
     */
    public int getColumns() {
        return this.columns;
    }

    /**
     * Determine the value to display at this cell.
     *
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return A ViewElement that details how to render the cell's formula.
     * @requires 0 ≤ row < getRows(), 0 ≤ column < getColumns().
     */
    public ViewElement valueAt(int row, int column) {
        for (Map.Entry<String, Expression> eachCell : rowColumnInput.entrySet()) {
            String key = eachCell.getKey();
            int cellRow = Integer.parseInt(key.split(",")[0]);
            int cellColumn = Integer.parseInt(key.split(",")[1]);
            if (row == cellRow && column == cellColumn) {
                return new ViewElement(eachCell.getValue().render(), "white", "black");
            }
        }
        return new ViewElement(this.defaultExpression.render(), "white", "black");
    }

    /**
     * Determine the formula to display at this cell.
     *
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return A ViewElement that details how to render the cell's formula.
     * @requires 0 ≤ row < getRows(), 0 ≤ column < getColumns().
     */
    public ViewElement formulaAt(int row, int column) {
        return valueAt(row, column);
    }

}
