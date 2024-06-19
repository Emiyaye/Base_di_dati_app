
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

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
        a.OP1_addAccount("Poly", "primoTest.email", "A", LocalDate.of(2000, 10, 1), 0, "IT", 1, Optional.of(1000), Optional.of(2000), 1);
    }
    
}
