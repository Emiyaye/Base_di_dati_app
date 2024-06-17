package View;

import javax.swing.JFrame;

public class UserFrame extends ViewImpl {

    private static final String TITLE = "UserMode";

    /**
     * Creating the frame for the Menu.
     */
    public UserFrame() {
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setContentPane();
    }

}
