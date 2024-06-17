package View;

import javax.swing.JFrame;

public class ViewImpl extends JFrame implements View {

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
