import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Rental {
    private int car_ID;
    private int customer_ID;
    private float total_cost;

    public static void rentCar(int customer_ID, int car_ID, Connection connection) {
        String query = "INSERT INTO rental  (customer_id,carID) VALUES(?,?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customer_ID);
            ps.setInt(2, car_ID);
            int affectedRowsCount = ps.executeUpdate();

            if (affectedRowsCount > 0) {
                System.out.println("Car rented succesfully!!!");
                return;
            } else {
                System.out.println("Unable to rent a car. Please check the inputs.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void showRentalHistory(Connection connection) {
        String query = "select r.id,cust.customer_id,cust.name,car.carID,car.make,car.model,r.rental_date,r.return_date,r.total_cost from car inner join rental as r on car.carID=r.carID inner join customer as cust on cust.customer_id=r.customer_id";

        try(PreparedStatement ps = connection.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();

            System.out.println("+-----+--------------+-----------------+------------+----------+----------+--------------+-------------+-------+");
            System.out.println("| ID  |  Customer ID |  Customer Name  |  Car ID    |  Make    | Model    | Rental Date  | Return Date | Cost  |");
            System.out.println("+-----+--------------+-----------------+------------+----------+----------+--------------+-------------+-------+");
            while(rs.next()){
                System.out.printf("|%-5d|%-14d|%-17s|%-12d|%-10s|%-10s|%-14s|%-13s|%-7d|\n",rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getDate(7),rs.getDate(8),rs.getInt(9));
            }
            System.out.println("+-----+--------------+-----------------+------------+----------+----------+--------------+-------------+-------+");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public static void returnCar(int car_id, Connection connection) {

    }
}
