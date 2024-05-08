import java.time.LocalDate;
import java.time.Period;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Rental {
    private int car_ID;
    private int customer_ID;
    private float total_cost;
    private static float dailyRate = 200;

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

    public static int checkValidTransaction(int carID,int custID,Connection connection){
        String query = "select id from rental where carID=? and customer_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1,carID) ;
            ps.setInt(2,custID);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return  rs.getInt("id"); //returns the ID of existing transaction
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private static void updateTotal(LocalDate startDate,int transaction_id,Connection connection){
        //get current date
        LocalDate currentDate = LocalDate.now();



        // Calculate the difference between the current date and the start date
        Period period = Period.between(startDate, currentDate);

        // Calculate the total number of days
        int totalDays = period.getDays();

        // Calculate the total cost
        float totalCost = totalDays * dailyRate;

        String updatePrice = "update rental set total_cost = ? where id = ? ";
        try(PreparedStatement ps = connection.prepareStatement(updatePrice)){
            ps.setFloat(1,totalCost);
            ps.setInt(2,transaction_id);
            int rowAffected = ps.executeUpdate();
            if (rowAffected > 0){
                return;
            }
            else{
                System.out.println("Error updating price");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }


    public static void returnCar(int transaction_id, Connection connection) {
        String update_query="update rental set return_date = CURRENT_DATE where id = ?";
        String getDate = "select rental_date from rental where id = ?";
        try ( PreparedStatement pstmt = connection.prepareStatement(update_query)) {
            pstmt.setInt(1,transaction_id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected >  0) {
               
                try(PreparedStatement ps = connection.prepareStatement(getDate)){
                    ps.setInt(1,transaction_id);
                    ResultSet resultSet = ps.executeQuery();
                    
                    if(resultSet.next()){
                        
                        LocalDate rentalDate=resultSet.getDate(1).toLocalDate();
                        System.out.println(rentalDate);
                        
                        updateTotal(rentalDate,transaction_id,connection);  
                    }
        
                }catch(SQLException e){
                    System.out.println("catch vitrta");
                    System.out.println(e.getMessage());
                }
            }
        }catch(SQLException e){
            System.out.println("last ko catch ho");
            System.out.println(e.getMessage());
        }
    }
}
