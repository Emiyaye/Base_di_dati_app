package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import data.Dati.Op18Data;

public class AdminPanel extends JPanel {

    private final JButton back;
    private final JButton OP18_ServiceTurnover;
    private JPanel LastPanel;

    public AdminPanel() {
        back = new JButton("BACK");
        LastPanel = new JPanel();
        setLayout(new BorderLayout());
        final JPanel westPanel = new JPanel(new BorderLayout());
        final JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(back);
        westPanel.add(backButtonPanel, BorderLayout.SOUTH);
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        OP18_ServiceTurnover = new JButton("OP18 Service Turnover by year");
        buttonPanel.add(OP18_ServiceTurnover);

        westPanel.add(buttonPanel, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
        add(LastPanel, BorderLayout.CENTER);
    }

    public void addOp18Listener (final ActionListener ac) {
        OP18_ServiceTurnover.addActionListener(ac);
    }
 
    public void addBackListener(final ActionListener ac) {
        back.addActionListener(ac);
    }

    public void updateCenterPanel(final JPanel panel) {
        remove(LastPanel);
        LastPanel = panel;
        add(LastPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

}
