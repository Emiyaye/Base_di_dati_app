package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * JPanel used for the StartMenu frame.
 */
public class MenuPanel extends JPanel {

    private static final String ADMIN = "Admin";
    private static final String USER = "User";
    private static final String QUIT = "Quit";
    private static final String FONT_STYLE = "Serif";
    private static final double WIDTH_PERC = 0.8;
    private static final double HEIGHT_PERC = 0.8;
    private static final double BUTTON_WIDTH_PERC = WIDTH_PERC * 0.2;
    private static final double BUTTON_HEIGHT_PERC = HEIGHT_PERC * 0.1;
    private static final int FONT_SIZE = 24;

    private final Dimension dimension;
    private final JButton admin;
    private final JButton user;
    private final JButton quit;

    /**
     * The Menu Panel, will show admin , user and quit buttons.
     */
    public MenuPanel() {
        this.dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(new Dimension(Double.valueOf(dimension.getWidth() * WIDTH_PERC).intValue(),
                Double.valueOf(dimension.getHeight() * HEIGHT_PERC).intValue()));
        admin = createButton(ADMIN, getButtonDimension());
        user = createButton(USER, getButtonDimension());
        quit = createButton(QUIT, getButtonDimension());

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        addButton(buttonPanel, admin, gbc);
        addButton(buttonPanel, user, gbc);
        addButton(buttonPanel, quit, gbc);

        this.add(buttonPanel);
    }

    public void addAdminListener(final ActionListener ac) {
        admin.addActionListener(ac);
    }

    public void addUserListener(final ActionListener ac) {
        user.addActionListener(ac);
    }

    public void addQuitListener(final ActionListener ac) {
        quit.addActionListener(ac);
    }

    /**
     * Create a JButton.
     * 
     * @param name name of the button
     * @param dim  dimension of the button
     * @return a JButton
     */
    private JButton createButton(final String name, final Dimension dim) {
        final JButton button = new JButton(name);
        button.setPreferredSize(dim);
        button.setFont(new Font(FONT_STYLE, Font.BOLD, FONT_SIZE));
        return button;
    }

    private Dimension getButtonDimension() {
        return new Dimension(Double.valueOf(dimension.getWidth() * BUTTON_WIDTH_PERC).intValue(),
                Double.valueOf(dimension.getHeight() * BUTTON_HEIGHT_PERC).intValue());
    }

    private void addButton(final JPanel panel, final JButton jb, final GridBagConstraints gbc) {
        panel.add(jb, gbc);
        gbc.gridx++;
    }

}
