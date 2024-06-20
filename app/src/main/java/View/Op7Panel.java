package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import data.Dati;

public class Op7Panel extends JPanel {
    private final JTextField songNameField;
    private final JButton searchButton;
    private final JButton cancelButton;
    private final JTable resultTable;
    private final DefaultTableModel tableModel;

    public Op7Panel() {
        setLayout(new BorderLayout());

        final JPanel inputPanel = new JPanel(new FlowLayout());
        songNameField = new JTextField(20);
        searchButton = new JButton("Search");
        cancelButton = new JButton("Cancel");

        inputPanel.add(new JLabel("Song Name:"));
        inputPanel.add(songNameField);
        inputPanel.add(cancelButton);
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[] { "codiceBrano", "Numero", "Titolo", "NumRiproduzioni", "Durata", "Esplicito",
                "FonteCrediti", "FileAudio", "CodicePubblicazione" }, 0);
        resultTable = new JTable(tableModel);

        add(new JScrollPane(resultTable), BorderLayout.CENTER);

        cancelButton.addActionListener(e -> songNameField.setText(""));
    }

    public void addSearchListener (final ActionListener ac){
        searchButton.addActionListener(ac);
    }

    public String getOp7Data (){
        return songNameField.getText();
    }

    public void updateTable(final List<Dati.Op7Data> results) {
        tableModel.setRowCount(0);
        for (final Dati.Op7Data data : results) {
            tableModel.addRow(new Object[] { data.codiceBrano(), data.numero(), data.titolo(), data.numRiproduzioni(), data.durata(),
                    data.esplicito(), data.fonteCrediti(), data.fileAudio(), data.codicePubblicazione() });
        }
    }
}