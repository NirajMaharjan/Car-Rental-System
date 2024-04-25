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

                System.out.printf("|%-5d|%-10s|%-10s|%-10d|%-10s|%-15d|\n",car.id,car.make,car.model,car.year,car.color,car.liscensePlate);

            }
            System.out.println("+-----+----------+----------+----------+----------+----------------+");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
