package View;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Graphical representation of the start Menu.
 */
public class MainFrame extends JFrame {

    private static final String TITLE = "Spotify";
    private static final String QUIT = "Quit";
    private final MenuPanel menu;

    /**
     * Creating the frame for the Menu.
     */
    public MainFrame(final Runnable onClose) {
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.menu = new MenuPanel();
        this.menu.addQuitListener(e -> {
            final String[] options = { "Yes", "No" };
            final var result = JOptionPane.showOptionDialog(this, "Do you want to QUIT?",
                    QUIT,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null, options, 0);
            if (result == 0) {
                onClose.run();
                dispose();
            }
        });
        // close connection
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose.run();
                System.exit(0);
            }
        });
        this.setContentPane(menu);
    }

    public void display() {
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void addAdminListener(final ActionListener ac) {
        this.menu.addAdminListener(ac);
    }

    public void addUserListener(final ActionListener ac) {
        this.menu.addUserListener(ac);
    }
}
