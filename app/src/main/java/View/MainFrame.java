package View;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {

    private static final String TITLE = "Spotify";
    private static final String QUIT = "Quit";
    private final MenuPanel menuPanel;
    private final AdminPanel adminPanel;
    private final UserPanel userPanel;

    public MainFrame(final Runnable onClose) {
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.menuPanel = new MenuPanel();
        this.adminPanel = new AdminPanel();
        this.userPanel = new UserPanel();
        this.menuPanel.addQuitListener(e -> {
            final String[] options = { "Yes", "No" };
            final var result = JOptionPane.showOptionDialog(this, "Do you want to QUIT?",
                    QUIT,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null, options, 0);
            if (result == 0) {
                onClose.run();
                System.out.println("Connection closed successfully");
                dispose();
            }
        });
        this.menuPanel.addAdminListener(e -> resetContentPane(adminPanel));
        this.menuPanel.addUserListener(e -> resetContentPane(userPanel));

        // close connection
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose.run();
                System.out.println("Connection closed successfully");
                System.exit(0);
            }
        });
        this.setContentPane(menuPanel);
    }

    public void display() {
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public UserPanel getUserPanel() {
        return this.userPanel;
    }

    public AdminPanel getAdminPanel() {
        return this.adminPanel;
    }

    private final void resetContentPane(final Container c) {
        this.setContentPane(c);
        this.revalidate();
        this.repaint();
    }
}
