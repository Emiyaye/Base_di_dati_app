package View;

import java.awt.GridBagConstraints;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import data.Dati;

public class Op11Panel extends AbstractInsertPanel<Dati.Op11Data> {
    private final JTextArea testoArea;

    public Op11Panel() {
        super(new String[]{"Codice Brano", "Testo"}, "Insert", "Cancel");

        // remove the JTextArea and put a bigger one!
        final Map<String, JTextField> textFieldMap = getTextFieldMap();
        final GridBagConstraints gbc = getGbc();

        remove(textFieldMap.get("Testo"));

        testoArea = new JTextArea(5, 30);
        testoArea.setLineWrap(true);
        testoArea.setWrapStyleWord(true);
        final JScrollPane scrollPane = new JScrollPane(testoArea);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);
        
        final var temp = getCancelButton();
        temp.addActionListener(e -> {
            clearFields();
            testoArea.setText("");
        });
    }

    @Override
    public Dati.Op11Data getData() {
        final Map<String, JTextField> textFieldMap = getTextFieldMap();
        final int codiceBrano = Integer.parseInt(textFieldMap.get("Codice Brano").getText());
        final String testo = testoArea.getText();
        return new Dati.Op11Data(codiceBrano, testo);
    }
}

