
import java.sql.Connection;
import java.sql.SQLException;

import Controller.MenuController;
import View.MainFrame;
import data.DAOUtils;

public final class App {

    public static void main(final String[] args) throws SQLException {
        // TODO: change test to spotify when complete
        
        final Connection connection = DAOUtils.localMySQLConnection("test", "root", "");
        final MainFrame mainFrame = new MainFrame(() -> {
            try {
                connection.close();
            } catch (Exception e) {
            }
        });
        new MenuController(mainFrame);
        mainFrame.display();

    }
}
