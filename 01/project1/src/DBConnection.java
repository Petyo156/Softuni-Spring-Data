import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection connection;

    public DBConnection() {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "mysql");
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/minions_db", properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getCollection(){
        return connection;
    }
}
