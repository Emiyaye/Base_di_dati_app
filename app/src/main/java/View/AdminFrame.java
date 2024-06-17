package View;

import javax.swing.JFrame;

public class AdminFrame extends ViewImpl{

    private static final String TITLE = "AdminMode";

    /**
     * Creating the frame for the Menu.
     */
    public AdminFrame() {
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setContentPane();
    }
}
