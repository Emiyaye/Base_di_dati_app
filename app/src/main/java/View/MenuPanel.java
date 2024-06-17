package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controller.MenuController;


/**
 * JPanel used for the StartMenu frame.
 */
public class MenuPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final String ADMIN = "Admin";
    private static final String USER = "User";
    private static final String QUIT = "Quit";
    private static final String FONT_STYLE = "Serif";
    private static final double WIDTH_PERC = 0.5;
    private static final double HEIGHT_PERC = 0.5;
    private static final double BUTTON_WIDTH_PERC = WIDTH_PERC * 0.2;
    private static final double BUTTON_HEIGHT_PERC = HEIGHT_PERC * 0.1;
    private static final int FONT_SIZE = 24;

    private final Dimension dimension;

    /**
     * The Menu Panel, will show the start, quit and rules buttons.
     * 
     * @param controller controller for the Menu
     * @param frame      frame for the Menu
     */
    public MenuPanel(final MenuController controller, final StartMenu frame) {
        this.dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(new Dimension(Double.valueOf(dimension.getWidth() * WIDTH_PERC).intValue(),
                Double.valueOf(dimension.getHeight() * HEIGHT_PERC).intValue()));

        final JButton admin = createButton(ADMIN, getButtonDimension());
        final JButton user = createButton(USER, getButtonDimension());
        final JButton quit = createButton(QUIT, getButtonDimension());
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        admin.addActionListener(e -> {
            
        });

        user.addActionListener(e ->{});

        quit.addActionListener(e -> {
            final String[] options = { "Yes", "No" };
            final var result = JOptionPane.showOptionDialog(this, "Do you want to QUIT?",
                    QUIT,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null, options, 0);
            if (result == 0) {
                frame.dispose();
            }
        });
        addButton(buttonPanel, admin, gbc);
        addButton(buttonPanel, user, gbc);
        addButton(buttonPanel, quit, gbc);

        this.add(buttonPanel);
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
