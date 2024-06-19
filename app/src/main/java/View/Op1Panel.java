package View;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Op1Data;

public class Op1Panel extends JPanel {

    private final JTextField nicknameField;
    private final JTextField emailField;
    private final JTextField passwordField;
    private final JTextField dataNascitaField;
    private final JTextField genereField;
    private final JTextField nazioneField;
    private final JTextField tipoPagamentoField;
    private final JTextField numeroCartaField;
    private final JTextField scadenzaCartaField;
    private final JTextField tipoAbbonamentoField;
    private final JButton cancelButton;
    private final JButton insertButton;

    public Op1Panel() {

        setLayout(new GridLayout(12, 2));

        nicknameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JTextField();
        dataNascitaField = new JTextField();
        genereField = new JTextField();
        nazioneField = new JTextField();
        tipoPagamentoField = new JTextField();
        numeroCartaField = new JTextField();
        scadenzaCartaField = new JTextField();
        tipoAbbonamentoField = new JTextField();
        cancelButton = new JButton("Cancel");
        insertButton = new JButton("Insert");

        add(new JLabel("Nickname:"));
        add(nicknameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Data di Nascita (YYYY-MM-DD):"));
        add(dataNascitaField);
        add(new JLabel("Genere (M / F):"));
        add(genereField);
        add(new JLabel("Nazione (Es: IT):"));
        add(nazioneField);
        add(new JLabel("Tipo Pagamento (1 - 3):"));
        add(tipoPagamentoField);
        add(new JLabel("Numero Carta (Optional):"));
        add(numeroCartaField);
        add(new JLabel("Scadenza Carta (Optional):"));
        add(scadenzaCartaField);
        add(new JLabel("Tipo Abbonamento (1 - 6):"));
        add(tipoAbbonamentoField);
        cancelButton.addActionListener(e -> clearFields());
        add(cancelButton);
        add(insertButton);
    }

    public void addInsertListener (final ActionListener ac){
        insertButton.addActionListener(ac);
    }

    public Op1Data getOp1Data() {
        return new Op1Data(
            nicknameField.getText(),
            emailField.getText(),
            passwordField.getText(),
            LocalDate.parse(dataNascitaField.getText()),
            genereField.getText(),
            nazioneField.getText(),
            Integer.parseInt(tipoPagamentoField.getText()),
            numeroCartaField.getText().isEmpty() ? Optional.empty() : Optional.of(Integer.parseInt(numeroCartaField.getText())),
            scadenzaCartaField.getText().isEmpty() ? Optional.empty() : Optional.of(Integer.parseInt(scadenzaCartaField.getText())),
            Integer.parseInt(tipoAbbonamentoField.getText())
        );
    }

    private void clearFields() {
        nicknameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        dataNascitaField.setText("");
        genereField.setText("");
        nazioneField.setText("");
        tipoPagamentoField.setText("");
        numeroCartaField.setText("");
        scadenzaCartaField.setText("");
        tipoAbbonamentoField.setText("");
    }
}
