package View;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Op3Data;

public class Op3Panel extends JPanel {
    private JTextField accountSeguitoField;
    private JTextField accountSeguenteField;
    private JButton followButton;
    private JButton cancelButton;

    public Op3Panel() {

        setLayout(new GridLayout(3, 2));

        accountSeguitoField = new JTextField();
        accountSeguenteField = new JTextField();
        followButton = new JButton("Follow");
        cancelButton = new JButton("Cancel");

        add(new JLabel("Account Seguito:"));
        add(accountSeguitoField);
        add(new JLabel("Account Seguente:"));
        add(accountSeguenteField);
        add(followButton);
        add(cancelButton);

        cancelButton.addActionListener(e -> clearFields());
    }

    public void addFollowListener (ActionListener ac){
        followButton.addActionListener(ac);
    }

    public Op3Data getOp3Data(){
        return new Op3Data(accountSeguitoField.getText(), accountSeguenteField.getText());
    }

    private void clearFields() {
        accountSeguitoField.setText("");
        accountSeguenteField.setText("");
    }}
