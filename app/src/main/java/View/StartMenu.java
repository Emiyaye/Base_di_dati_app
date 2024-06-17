package View;

import javax.swing.JFrame;

import Controller.MenuControllerImpl;

/**
 * Graphical representation of the start Menu.
 */
public class StartMenu extends JFrame implements View {

    private static final long serialVersionUID = 1L;
    private static final String TITLE = "Spotify";

    /**
     * Creating the frame for the Menu.
     */
    public StartMenu() {
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new MenuPanel(new MenuControllerImpl(), this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
