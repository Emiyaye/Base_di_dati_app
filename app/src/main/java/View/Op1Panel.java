package View;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Op1Panel extends JPanel{
    
    private JTextField nicknameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JTextField dataNascitaField;
    private JTextField genereField;
    private JTextField nazioneField;
    private JTextField tipoPagamentoField;
    private JTextField numeroCartaField;
    private JTextField scadenzaCartaField;
    private JTextField tipoAbbonamentoField;
    private JButton cancelButton;
    private JButton insertButton;
    

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
        add(new JLabel("Genere:"));
        add(genereField);
        add(new JLabel("Nazione:"));
        add(nazioneField);
        add(new JLabel("Tipo Pagamento:"));
        add(tipoPagamentoField);
        add(new JLabel("Numero Carta:"));
        add(numeroCartaField);
        add(new JLabel("Scadenza Carta:"));
        add(scadenzaCartaField);
        add(new JLabel("Tipo Abbonamento:"));
        add(tipoAbbonamentoField);
        add(cancelButton);
        add(insertButton);
    }
}
