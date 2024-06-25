package View;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.Dati;

public class MainFrame extends JFrame {

    private static final String TITLE = "Spotify";
    private static final String QUIT = "Quit";
    private static final String ADMIN_MAIL = "admin@spotify.com";
    private static final String ADMIN_PASSWORD = "password";
    private final MenuPanel menuPanel;
    private final AdminPanel adminPanel;
    private final AdminLoginPanel adminLoginPanel;
    private final UserPanel userPanel;

    public MainFrame(final Runnable onClose) {
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.menuPanel = new MenuPanel();
        this.adminPanel = new AdminPanel();
        this.adminLoginPanel = new AdminLoginPanel();
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
        this.menuPanel.addAdminListener(e -> resetContentPane(adminLoginPanel));        
        this.adminLoginPanel.addSubmitListener(e -> {
            final Dati.AdminLogin data = this.adminLoginPanel.getData();
            if (data.adminEmail().equals(ADMIN_MAIL) && data.password().equals(ADMIN_PASSWORD)) {
                resetContentPane(adminPanel);
            } else {
                final StringBuilder sb = new StringBuilder();
                if (!data.adminEmail().equals(ADMIN_MAIL)) {
                    sb.append("Email errata");
                }
                if (!data.password().equals(ADMIN_PASSWORD)) {
                    if (!sb.isEmpty()) {
                        sb.append(" e ");
                    }
                    sb.append("Password errata");
                }
                JOptionPane.showMessageDialog(null, sb, "Fail",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        this.menuPanel.addUserListener(e -> resetContentPane(userPanel));
        this.userPanel.addBackListener(e -> resetContentPane(menuPanel));
        this.adminLoginPanel.addBackListener(e -> resetContentPane(menuPanel));
        this.adminPanel.addBackListener(e -> resetContentPane(menuPanel));

        // close connection
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
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
