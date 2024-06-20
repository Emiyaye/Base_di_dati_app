package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Function;

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
    private final JTable resultTable;
    private final DefaultTableModel tableModel;
    private final String[] attributeNames;
    private final Function<T, Object[]> function; //this function must give an Object[] with all the data

    public GeneralSelectPanel(final String label, final String[] attributeNames, final Function<T, Object[]> function) {
        this.attributeNames = attributeNames;
        this.function = function;
        setLayout(new BorderLayout());

        final JPanel inputPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        cancelButton = new JButton("Cancel");

        inputPanel.add(new JLabel(label + ":"));
        inputPanel.add(searchField);
        inputPanel.add(cancelButton);
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(this.attributeNames, 0);
        resultTable = new JTable(tableModel);

        add(new JScrollPane(resultTable), BorderLayout.CENTER);
        cancelButton.addActionListener(e -> searchField.setText(""));
    }

    public void addSearchListener(final ActionListener ac) {
        searchButton.addActionListener(ac);
    }

    public String getData() {
        return searchField.getText();
    }

    public void updateTable(final List<T> results) {
        tableModel.setRowCount(0);
        for (final var rowData : results) {
            tableModel.addRow(function.apply(rowData));
        }
    }
}
