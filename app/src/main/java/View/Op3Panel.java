package View;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Dati;

public class Op3Panel extends JPanel {
    private final JTextField accountSeguitoField;
    private final JTextField accountSeguenteField;
    private final JButton cancelButton;
    private final JButton followButton;

    public Op3Panel() {

        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        accountSeguitoField = new JTextField(15);
        accountSeguenteField = new JTextField(15);
        followButton = new JButton("Follow");
        cancelButton = new JButton("Cancel");

        addComponent(new JLabel("Account Seguito:"), gbc, 0, 0, GridBagConstraints.EAST);
        addComponent(accountSeguitoField, gbc, 1, 0, GridBagConstraints.WEST);
        addComponent(new JLabel("Account Seguente:"), gbc, 0, 1, GridBagConstraints.EAST);
        addComponent(accountSeguenteField, gbc, 1, 1, GridBagConstraints.WEST);
        addComponent(cancelButton, gbc, 0, 2, GridBagConstraints.EAST);
        addComponent(followButton, gbc, 1, 2, GridBagConstraints.WEST);

        cancelButton.addActionListener(e -> clearFields());
    }

    public void addFollowListener(final ActionListener ac) {
        followButton.addActionListener(ac);
    }

    public Dati.Op3Data getOp3Data() {
        return new Dati.Op3Data(accountSeguitoField.getText(), accountSeguenteField.getText());
    }

    private void addComponent(final Component component, final GridBagConstraints gbc,
            final int gridx, final int gridy, final int anchor) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = anchor;
        add(component, gbc);
    }

    private void clearFields() {
        accountSeguitoField.setText("");
        accountSeguenteField.setText("");
    }
}
