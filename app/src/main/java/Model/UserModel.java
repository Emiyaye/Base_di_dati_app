package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import data.DAOUtils;

public class UserModel {
    
    private final Connection connection;

    public UserModel (final Connection connection){
        Objects.requireNonNull(connection);
        this.connection = connection;
    }

    public void test (String a) {
        String query = "Insert into prova (name) values(?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, a);
            statement.executeUpdate();
            System.out.println("yes");

            statement.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
