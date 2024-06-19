package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;

public class UserPanel extends JPanel {

    private final JButton back;
    private final JButton OP1_CreateAccount;
    private Optional<JPanel> LastPanel;

    public UserPanel() {
        back = new JButton("BACK");
        LastPanel = Optional.empty();
        setLayout(new BorderLayout());
        final JPanel westPanel = new JPanel(new BorderLayout());
        final JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(back);
        westPanel.add(backButtonPanel, BorderLayout.SOUTH);
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        OP1_CreateAccount = new JButton("OP1 Create account");
        buttonPanel.add(OP1_CreateAccount);

        westPanel.add(buttonPanel, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
    }

    public void addBackListener(final ActionListener ac) {
        back.addActionListener(ac);
    }

    public void addOp1Listener(final ActionListener ac) {
        OP1_CreateAccount.addActionListener(ac);
    }

    public void UpdateEastPanel(final JPanel panel) {
        if (LastPanel.isEmpty()) {
            LastPanel = Optional.of(panel);
            add(LastPanel.get(), BorderLayout.EAST);
        } else {
            remove(LastPanel.get());
            LastPanel = Optional.of(panel);
            add(LastPanel.get(), BorderLayout.EAST);
        }
        revalidate();
        repaint();
    }

}
