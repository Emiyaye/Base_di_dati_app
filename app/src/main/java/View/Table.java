package View;

import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import java.util.function.Function;

public class Table<T> {
    private final String tableName;
    private final String[] attributeNames;
    private final Function<T, Object[]> function;
    private DefaultTableModel tableModel;

    public Table(final String tableName, final String[] attributeNames, final Function<T, Object[]> function) {
        this.tableName = tableName;
        this.attributeNames = attributeNames;
        this.function = function;
    }

    public String getTableName() {
        return tableName;
    }

    public String[] getAttributeNames() {
        return attributeNames;
    }

    public Function<T, Object[]> getFunction() {
        return function;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(final DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }
}
