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
    private final JButton OP3_FollowArtist;
    private final JButton OP7_SearchSongs;
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
        OP3_FollowArtist = new JButton("OP3 Follow Artist");
        buttonPanel.add(OP3_FollowArtist);
        OP7_SearchSongs = new JButton("OP7 Search Songs");
        buttonPanel.add(OP7_SearchSongs);

        westPanel.add(buttonPanel, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
    }

    public void addBackListener(final ActionListener ac) {
        back.addActionListener(ac);
    }

    public void addOp1Listener(final ActionListener ac) {
        OP1_CreateAccount.addActionListener(ac);
    }

    public void addOp3Listener(final ActionListener ac) {
        OP3_FollowArtist.addActionListener(ac);
    }

    public void addOp7Listener(final ActionListener ac) {
        OP7_SearchSongs.addActionListener(ac);
    }

    public void UpdateCenterPanel(final JPanel panel) {
        if (LastPanel.isEmpty()) {
            LastPanel = Optional.of(panel);
            add(LastPanel.get(), BorderLayout.CENTER);
        } else {
            remove(LastPanel.get());
            LastPanel = Optional.of(panel);
            add(LastPanel.get(), BorderLayout.CENTER);
        }
        revalidate();
        repaint();
    }

}
