import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Car {
    private int id;
    private String  make;
    private String model;
    private int year;
    private String color;
    private int liscensePlate;
    private boolean availability;



    //method to view all the available cars
    public static void viewAvailableCars(Connection connection){

        String query = "SELECT * FROM car where availability='true'";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("+-----+----------+----------+----------+----------+----------------+");
            System.out.println("| ID  |  Make    |  Model   |  Year    |  Color   | Liscense Plate |");
            System.out.println("+-----+----------+----------+----------+----------+----------------+");


            while(resultSet.next()){
                Car car = new Car();
                car.id = resultSet.getInt("carID");
                car.make = resultSet.getString("make");
                car.model = resultSet.getString("model");
                car.year = resultSet.getInt("manufractured_year");
                car.color = resultSet.getString("color");
                car.liscensePlate = resultSet.getInt("liscense_plate");

                System.out.printf("|%-5d|%-10s|%-10s|%-10d|%-10s|%-16d|\n",car.id,car.make,car.model,car.year,car.color,car.liscensePlate);
                //-5d states that | yo vanda 5 space agadi deki suru garne
            }
            System.out.println("+-----+----------+----------+----------+----------+----------------+");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public static boolean isValidCarId(int car_ID,Connection connection){
        boolean isValid = false;
        String query = "select count(*) as count from car where carID= ? and availability=0";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, car_ID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    isValid = count > 0; // If count > 0, car with the given ID exists
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return isValid;

    }


    public static void changeAvailability(int car_ID,Connection connection){
        
        String query = "UPDATE car SET availability = CASE availability WHEN 0 THEN 1 ELSE 0 END where carID = ?; "; //not toggles avaibility

        try(PreparedStatement ps = connection.prepareStatement(query)){
            
            ps.setInt(1,car_ID) ;
            int affectedRowsCount = ps.executeUpdate();
            if(affectedRowsCount == 0 ){
                System.out.println( "No rows were updated for availability.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    public static boolean checkAvailability(int carID,Connection connection){
        boolean isAvailable = true;
        String query = "select availability from car where carID=?";//return garda first check if its available or not
    
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1,carID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                isAvailable = rs.getBoolean("availability");
                //rs.getBoolean garda 0 lai false 1 lai true 
                //but mathi select * from car where availabity = true garda 0 lai true 
            }else{
                
                throw new RuntimeException("Car not found!");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return !isAvailable;
    
    }
    public static void searchByMake(String  make, Connection conn){
        String query ="SELECT * FROM Car WHERE Make = ? and  availability = 0";
        try ( PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1,make);
            ResultSet rs=stmt.executeQuery();
    
            if(!rs.next()){
                System.out.println("No  cars with the make "+make+" were found.");
                return;
            }
            
            System.out.println("+-----+----------+----------+----------+----------+----------------+");
            System.out.println("| ID  |  Make    |  Model   |  Year    |  Color   | Liscense Plate |");
            System.out.println("+-----+----------+----------+----------+----------+----------------+");
            
            //while rs.next() garda duichoti loop chalyo; if wala rs.next le first row point ani yeta while ko rs.next le second row ie first row is skipped

            do {
                System.out.printf("|%-5d|%-10s|%-10s|%-10d|%-10s|%-16d|\n",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getInt(6));
            }while(rs.next());
            System.out.println("+-----+----------+----------+----------+----------+----------------+");
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void returnCar(int carID,Connection connection){

    }
}







