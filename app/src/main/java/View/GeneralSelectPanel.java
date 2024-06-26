package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GeneralSelectPanel<T> extends JPanel {
    private final JTextField searchField;
    private final JButton searchButton;
    private final JButton cancelButton;
    private final JPanel inputPanel;
    private final JPanel tablesPanel;
    private final List<Table<T>> tables;

    public GeneralSelectPanel(final String label, final List<Table<T>> tables) {
        setLayout(new BorderLayout());

        inputPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        cancelButton = new JButton("Cancel");

        inputPanel.add(new JLabel(label + ":"));
        inputPanel.add(searchField);
        inputPanel.add(cancelButton);
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);

        // creating table panel
        tablesPanel = new JPanel(new GridLayout(1, 0)); // rows adj 
        this.tables = tables;
        for (final var table : this.tables) {
            final DefaultTableModel tableModel = new UnmodifiableTableModel(table.getAttributeNames(), 0);
            final JTable jtable = new JTable(tableModel);
            jtable.getTableHeader().setReorderingAllowed(false);
            final JScrollPane scrollPane = new JScrollPane(jtable);
            tablesPanel.add(scrollPane);
            table.setTableModel(tableModel);
        }

        add(tablesPanel, BorderLayout.CENTER);
        cancelButton.addActionListener(e -> searchField.setText(""));
    }

    public void addSearchListener(final ActionListener ac) {
        searchButton.addActionListener(ac);
    }

    protected void removeSearchField () {
        inputPanel.remove(searchField);
    }

    protected void removeSearchButton() {
        inputPanel.remove(searchButton);
    }

    protected void removeCancelButton() {
        inputPanel.remove(cancelButton);
    }

    public String getData() {
        return searchField.getText();
    }
    
    protected void setTextButtonSearch(String text) {
        searchButton.setText(text);
    }

    // String is the name of the table, each table has a name
    public void updateTable(final Map<String, List<T>> results) {
        // Iterating through each existing table
        for (final var table : tables) {
            final DefaultTableModel tableModel = table.getTableModel();
            tableModel.setRowCount(0);
            final List<T> tableData = results.get(table.getTableName());
            if (tableData != null) {
                for (final var row : tableData) {
                    tableModel.addRow(table.getFunction().apply(row));
                }
            }
        }
    }

    // makes the cell unmodifiable
    static class UnmodifiableTableModel extends DefaultTableModel {
        public UnmodifiableTableModel(String[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
