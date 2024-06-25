package View;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class AbstractInsertPanel<T> extends JPanel {
    private final Map<String, JTextField> textFieldMap;
    private final GridBagConstraints gbc;
    private final JButton cancelButton;
    private final JButton submitButton;

    public AbstractInsertPanel(final String[] fieldLabels, final String submitButtonText,
            final String cancelButtonText) {
        textFieldMap = new HashMap<>();
        setLayout(new GridBagLayout());
        this.gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;
        for (final String label : fieldLabels) {
            final JTextField textField = new JTextField(15);
            textFieldMap.put(label, textField);
            addComponent(new JLabel(label + ":"), gbc, 0, row, GridBagConstraints.EAST);
            addComponent(textField, gbc, 1, row, GridBagConstraints.WEST);
            row++;
        }

        submitButton = new JButton(submitButtonText);
        cancelButton = new JButton(cancelButtonText);

        addComponent(cancelButton, gbc, 0, row, GridBagConstraints.EAST);
        addComponent(submitButton, gbc, 1, row, GridBagConstraints.WEST);

        cancelButton.addActionListener(e -> clearFields());
    }

    public void addSubmitListener(final ActionListener ac) {
        submitButton.addActionListener(ac);
    }

    protected Map<String, JTextField> getTextFieldMap() {
        return new HashMap<>(textFieldMap);
    }

    protected GridBagConstraints getGbc() {
        return gbc;
    }

    protected JButton getCancelButton() {
        return cancelButton;
    }

    public abstract T getData();

    protected void addComponent(final Component component, final GridBagConstraints gbc,
            final int gridx, final int gridy, final int anchor) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = anchor;
        add(component, gbc);
    }

    protected void clearFields() {
        textFieldMap.values().forEach(e -> e.setText(""));
    }
}
