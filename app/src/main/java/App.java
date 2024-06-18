
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Model.UserModel;
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
        var a = new UserModel(connection);
        a.test("Poly");
    }
    
}
