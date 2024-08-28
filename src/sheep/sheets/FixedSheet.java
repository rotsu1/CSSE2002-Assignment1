package sheep.sheets;

import sheep.core.SheetView;
import sheep.core.SheetUpdate;
import sheep.core.UpdateResponse;
import sheep.core.ViewElement;

/**
 * Spreadsheet that has fixed values in every cell.
 */
public class FixedSheet implements SheetUpdate, SheetView {

    /**
     * The number of rows in the sheet.
     * A fixed sheet will always have exactly 6 rows.
     * @return 6
     */
    @Override
    public int getRows() {
        return 6;
    }

    /**
     * The number of columns in the sheet.
     * A fixed sheet will always have exactly 6 columns.
     * @return 6.
     */
    @Override
    public int getColumns() {
        return 6;
    }


    private ViewElement valueFormulaAt(int row, int column, String content1) {
        if ((row == 2 | row == 3) && (column == 2 | column == 3)) {
            return new ViewElement(content1, "green", "black");
        } else {
            return new ViewElement("", "white", "black");
        }
    }

    /**
     * Determine the value to display at this cell.
     *
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return An appropriately formatted cell based on whether it is highlighted or not.
     * @requires 0 ≤ row < getRows(), 0 ≤ column < getColumns().
     */
    @Override
    public ViewElement valueAt(int row, int column) {
        return valueFormulaAt(row, column, "W");
    }

    /**
     * Determine the formula to display at this cell.
     *
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return An appropriately formatted cell based on whether it is highlighted or not.
     * @requires 0 ≤ row < getRows(), 0 ≤ column < getColumns().
     */
    @Override
    public ViewElement formulaAt(int row, int column) {
        return valueFormulaAt(row, column, "GREEN");
    }

    /**
     * Attempt to update a cell in the position.
     *
     * @param row The row index to update.
     * @param column The column index to update.
     * @param input The value as a string to replace within the sheet.
     * @return A failed update as the sheet is view only.
     * @requires 0 ≤ row < getRows(), 0 ≤ column < getColumns(), input != null
     */
    @Override
    public UpdateResponse update(int row, int column, String input) {
        return UpdateResponse.fail("Sheet is view only.");
    }
}
