package dbintegration;

import java.sql.*;

public class Connect {

    public static ResultSet getDataFromDataBase(String sql){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/user/Dbconnect/letter_db.sqlite3");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return rs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
