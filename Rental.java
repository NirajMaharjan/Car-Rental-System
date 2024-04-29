import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Rental {
    private int car_ID;
    private int customer_ID;
    private float total_cost;

    public static void rentCar(int customer_ID,int car_ID,Connection connection){
        String query = "INSERT INTO rental  (customer_id,carID) VALUES(?,?)";

        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, customer_ID);
            ps.setInt(2, car_ID);
            int affectedRowsCount = ps.executeUpdate();

            if(affectedRowsCount>0){
                System.out.println("Car rented succesfully!!!");
                return;
            }
            else{
                System.out.println("Unable to rent a car. Please check the inputs.");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
