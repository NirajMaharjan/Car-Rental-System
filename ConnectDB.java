import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectDB{
    private static String USERNAME = "root";
    private static String PASSWORD = "Niraz123";
    private static String URL = "jdbc:mysql://localhost:3306/car_rental";

    public static Connection getConnection() throws Exception{
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);

    }
    
    public static void closeConnection(Connection connection){
            if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
