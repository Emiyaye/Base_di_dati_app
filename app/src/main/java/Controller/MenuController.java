package Controller;

import java.awt.Container;

import View.AdminPanel;
import View.StartMenu;
import View.UserPanel;

public class MenuController {

    private final StartMenu startMenu;
    private final AdminPanel adminPanel;
    private final UserPanel userPanel;

    public MenuController(final StartMenu startMenu) {
        this.startMenu = startMenu;
        this.adminPanel = new AdminPanel();
        this.userPanel = new UserPanel();
        this.startMenu.addAdminListener(e -> resetContentPane(adminPanel));
        this.startMenu.addUserListener(e -> resetContentPane(userPanel));
    }

    private final void resetContentPane(final Container c) {
        this.startMenu.setContentPane(c);
        this.startMenu.revalidate();
        this.startMenu.repaint();
    }

}