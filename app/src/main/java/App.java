
import java.sql.Connection;
import java.sql.SQLException;

import Controller.Controller;
import Model.Model;
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
        mainFrame.display();
        final Model userModel = new Model(connection);
        final Controller userController = new Controller(userModel, mainFrame.getUserPanel());
        userModel.Op7_searchSong("test");
    }

}
