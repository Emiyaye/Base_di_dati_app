package View;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import data.Dati;

public class AdminLoginPanel extends AbstractInsertPanel<Dati.AdminLogin> {
    private static final String FIELD1 = "Email";
    private static final String FIELD2 = "Password";
    private final JButton backButton;

    public AdminLoginPanel() {
        super(new String[] { FIELD1, FIELD2 }, "Insert", "Cancel");
        this.backButton = new JButton("Back");
        addComponent(backButton, getGbc(), 2, 2, GridBagConstraints.WEST);
        final var data = getTextFieldMap();
        data.get("Email").setText("admin@spotify.com");
        data.get("Password").setText("password");
    }

    public void addBackListener(final ActionListener ac) {
        backButton.addActionListener(ac);
    }

    @Override
    public Dati.AdminLogin data() {
        final var data = getTextFieldMap();
        return new Dati.AdminLogin(data.get(FIELD1).getText(), data.get(FIELD2).getText());
    }
}
