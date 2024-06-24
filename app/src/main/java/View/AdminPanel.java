package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AdminPanel extends JPanel {

    private final JButton back;
    private final JButton OP12_DisableEnableAccount;
    private final JButton OP16_viewActiveAbbonamento;
    private final JButton OP17_PopularArtist;
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

        OP12_DisableEnableAccount = new JButton("OP12_DisableEnableAccount");
        buttonPanel.add(OP12_DisableEnableAccount);

        OP16_viewActiveAbbonamento = new JButton("OP16 View Active Abbonamento");
        buttonPanel.add(OP16_viewActiveAbbonamento);

        OP17_PopularArtist = new JButton("OP17 Top 5 most popular Artist");
        buttonPanel.add(OP17_PopularArtist);

        OP18_ServiceTurnover = new JButton("OP18 Service Turnover by year");
        buttonPanel.add(OP18_ServiceTurnover);

        westPanel.add(buttonPanel, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
        add(LastPanel, BorderLayout.CENTER);
    }

    public void addOp12Listener (final ActionListener ac) {
        OP12_DisableEnableAccount.addActionListener(ac);
    }

    public void addOp16Listener (final ActionListener ac) {
        OP16_viewActiveAbbonamento.addActionListener(ac);
    }

    public void addOp17Listener (final ActionListener ac) {
        OP17_PopularArtist.addActionListener(ac);
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
