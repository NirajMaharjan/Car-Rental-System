import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class jdbcDemo {
    public static String USERNAME = "root";
    public static String PASSWORD = "";
    public static String URL = "jdbc:mysql://localhost:3306/carRental";
    public static void main(String[] args) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement stmt = con.createStatement();
        stmt.executeUpdate("insert into admin values(45,'Tata',22000);");
        System.out.println("Insert Completed");
    }
}