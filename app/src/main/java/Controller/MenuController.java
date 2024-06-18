package Controller;

import java.awt.Container;

import View.AdminPanel;
import View.MainFrame;
import View.UserPanel;

public class MenuController {

    private final MainFrame mainFrame;
    private final AdminPanel adminPanel;
    private final UserPanel userPanel;

    public MenuController(final MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.adminPanel = new AdminPanel();
        this.userPanel = new UserPanel();
        this.mainFrame.addAdminListener(e -> resetContentPane(adminPanel));
        this.mainFrame.addUserListener(e -> resetContentPane(userPanel));
    }

    private final void resetContentPane(final Container c) {
        this.mainFrame.setContentPane(c);
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

}