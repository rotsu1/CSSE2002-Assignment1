package sheep.sheets;

import sheep.core.SheetUpdate;
import sheep.core.SheetView;
import sheep.core.UpdateResponse;
import sheep.core.ViewElement;
import sheep.expression.Expression;
import sheep.expression.TypeError;
import sheep.parsing.ParseException;
import sheep.parsing.Parser;

import java.util.*;

/**
 * Spreadsheet that evaluates its expressions and updates dependant cells.
 * Sheet is an implementation of a spreadsheet capable of evaluating its expressions.
 */
public class Sheet implements SheetView, SheetUpdate {

    /**
     * The parser instance used to create expressions.
     */
    private final Parser parser;

    /**
     * A mapping of built-in identifiers to expressions.
     */
    private final Map<String, Expression> builtIns;

    /**
     * The default expression to load in every cell.
     */
    private final Expression defaultExpression;

    /**
     * Amount of rows for the new sheet.
     */
    private final int rows;

    /**
     * Amount of columns for the new sheet.
     */
    private final int columns;

    private final Map<CellLocation, Expression> expressionAtLocation = new HashMap<>();

    private final Set<CellLocation> usedCell = new HashSet<>();

    /**
     * Constructs a new instance of the sheet class.
     * A sheet should initially be populated in every cell with the defaultExpression.
     *
     * @param parser The parser instance used to create expressions.
     * @param builtIns A mapping of built-in identifiers to expressions.
     * @param defaultExpression The default expression to load in every cell.
     * @param rows Amount of rows for the new sheet.
     * @param columns  Amount of columns for the new sheet.
     * @requires rows > 0, columns > 0 && columns < 26.
     */
    Sheet(Parser parser, Map<String, Expression> builtIns,
          Expression defaultExpression, int rows, int columns) {
        this.parser = parser;
        this.builtIns = builtIns;
        this.defaultExpression = defaultExpression;
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * The number of rows in the spreadsheet.
     *
     * @return The number of rows for this spreadsheet.
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * The number of columns in the spreadsheet.
     *
     * @return The number of columns for this spreadsheet.
     */
    public int getColumns() {
        return this.columns;
    }

    /**
     * The value to render at this location.
     * The content of the ViewElement should correspond to the result of the
     * Expression.render() method on valueAt(CellLocation).
     *
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @return The value to render at this location.
     * @requires location is within the bounds (row/columns) of the spreadsheet.
     */
    public ViewElement valueAt(int row, int column) {
        CellLocation newCell = new CellLocation(row, column);
        if (expressionAtLocation.containsKey(newCell)) {
            return new ViewElement(valueAt(newCell).render(),
                    "white", "black");
        }
        return new ViewElement(this.defaultExpression.render(), "white", "black");
    }

    /**
     * The formula to render at this location.
     * The content of the ViewElement should correspond to the result of the
     * Expression.render() method on formulaAt(CellLocation).
     *
     * @param row A row within the spreadsheet.
     * @param column A column within the spreadsheet.
     * @return The formula to render at this location.
     * @requires location is within the bounds (row/columns) of the spreadsheet.
     */
    public ViewElement formulaAt(int row, int column) {
        for (Map.Entry<CellLocation, Expression> eachCell : expressionAtLocation.entrySet()) {
            if (row == eachCell.getKey().getRow() && column == eachCell.getKey().getColumn()) {
                return new ViewElement(formulaAt(eachCell.getKey()).render(),
                        "white", "black");
            }
        }
        return new ViewElement(this.defaultExpression.render(), "white", "black");
    }

    /**
     * Attempt to update the cell at row and column within the sheet with the given input.
     *
     * @param row The row index to update.
     * @param column The column index to update.
     * @param input The value as a string to replace within the sheet.
     * @return Information about the status of performing the update.
     */
    public UpdateResponse update(int row, int column, String input) {
        try {
            Expression parsed = this.parser.parse(input);
            for (Map.Entry<CellLocation, Expression> eachCell : expressionAtLocation.entrySet()) {
                builtIns.put(eachCell.getKey().toString(), eachCell.getValue());
            }
            update(new CellLocation(row, column), parsed);
        } catch (ParseException e) {
            return UpdateResponse.fail(String.format("Unable to parse: [%s]", input));
        } catch (TypeError e) {
            return UpdateResponse.fail(String.format("Type error: [%s]", e));
        }
        return UpdateResponse.success();
    }

    /**
     * The formula expression currently stored at the location in the spreadsheet.
     *
     * @param location A cell location within the spreadsheet.
     * @return The formula expression at the given cell location.
     * @requires location is within the bounds (row/columns) of the spreadsheet.
     */
    public Expression formulaAt(CellLocation location) {
        return expressionAtLocation.get(location);
    }

    /**
     * The value expression currently stored at the location in the spreadsheet.
     *
     * @param location A cell location within the spreadsheet.
     * @return The value expression at the given cell location.
     * @requires location is within the bounds (row/columns) of the spreadsheet.
     */
    public Expression valueAt(CellLocation location) {
        return expressionAtLocation.get(location);
    }

    /**
     * Determine which cells are used by the formula at the given cell location.
     *
     * @param location A cell location within the spreadsheet.
     * @return All the cells which use the given cell as a dependency.
     * @requires location is within the bounds (row/columns) of the spreadsheet.
     */
    public Set<CellLocation> usedBy(CellLocation location) {
        for (CellLocation dependentLocation : expressionAtLocation.keySet()) {
            for (String cell : formulaAt(dependentLocation).dependencies()) {
                if (Objects.equals(cell, location.toString())) {
                    usedCell.add(dependentLocation);
                    usedBy(dependentLocation);
                }
            }
        }
        return usedCell;
    }

    /**
     * Insert an expression into a cell location, updating the sheet as required.
     *
     * @param location A cell location to insert the expression into the sheet.
     * @param cell An expression to insert at the given location.
     * @throws TypeError If the evaluation of the inserted cell or any of its usages results in a
     *                  TypeError being thrown.
     * @requires location is within the bounds (row/columns) of the spreadsheet.
     */
    public void update(CellLocation location, Expression cell) throws TypeError {
        for (Map.Entry<CellLocation, Expression> eachCell : expressionAtLocation.entrySet()) {
            builtIns.put(eachCell.getKey().toString(), eachCell.getValue());
        }
        try {
            expressionAtLocation.put(location, cell.value(builtIns));
        } catch (TypeError e) {
            expressionAtLocation.put(location, cell);
        }
    }
}