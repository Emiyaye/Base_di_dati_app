
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import View.StartMenu;
import data.DAOUtils;

public final class App {

    public static void main(String[] args) throws SQLException {
        //TODO: change test to spotify when complete
        /* var connection = DAOUtils.localMySQLConnection("test", "root", "");

        Statement statement = connection.createStatement();

        ResultSet resultet = statement.executeQuery("select * from prova");
        while (resultet.next()){
            System.out.println(resultet.getInt(1) + " " + resultet.getString(2));
        }
        connection.close(); */
        StartMenu startMenu = new StartMenu();
        startMenu.display();
    
    }
}
