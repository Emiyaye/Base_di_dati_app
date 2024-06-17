package View;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Graphical representation of the start Menu.
 */
public class StartMenu extends ViewImpl {

    private static final String TITLE = "Spotify";
    private static final String QUIT = "Quit";
    private final MenuPanel menu;

    /**
     * Creating the frame for the Menu.
     */
    public StartMenu() {
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
                dispose();
            }
        });
        this.setContentPane(menu);
    }

    public void addAdminListener(final ActionListener ac) {
        this.menu.addAdminListener(ac);
    }
}
