package View;

import javax.swing.JFrame;

import Controller.MenuControllerImpl;

/**
 * Graphical representation of the start Menu.
 */
public class StartMenu extends ViewImpl {

    private static final String TITLE = "Spotify";

    /**
     * Creating the frame for the Menu.
     */
    public StartMenu() {
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new MenuPanel(new MenuControllerImpl(), this));
    }
}
