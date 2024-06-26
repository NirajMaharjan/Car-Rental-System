import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

public class RentalSystem {

    // Menu lists
    private static void menuList() {
        System.out.println("Welcome to the Car Rental System!");
        System.out.println("Please enter your choice:");
        System.out.println("1. View available cars");
        System.out.println("2. Rent a car");
        System.out.println("3. Return a car");
        System.out.println("4. View Rental history");
        System.out.println("5. Search for a car ");
        System.out.println("0. Exit");
    }

    // main function
    public static void main(String[] args) {
        Connection connection = null;
        Scanner scan = new Scanner(System.in);
        boolean isRunning = true;

        try {
            connection = ConnectDB.getConnection();
            while (isRunning) {

                menuList();
                int ch = scan.nextInt();
                switch (ch) {

                    // view all the available cars only
                    case 1:

                        Car.viewAvailableCars(connection);
                        

                        break;

                    /*
                     * car renting process
                     * asks the customer liscense number -> if not existing customer then ask all
                     * the ddetails
                     * gets the car id and customer id
                     * change the availability of corresponding car id to false
                     * records the rent in rental table
                     */
                    case 2:
                        int customer_ID = Customer.askDetails(scan, connection);
                        if (customer_ID != -1) {
                            System.out.println("Enter the Car ID: ");
                            int car_ID = scan.nextInt();
                            if (Car.isValidCarId(car_ID, connection) && Car.checkAvailability(car_ID, connection)) {
                                Car.changeAvailability(car_ID, connection);
                                Rental.rentCar(customer_ID, car_ID, connection);

                            } else {
                                boolean check = Car.checkAvailability(car_ID, connection);
                                System.out.println(check);
                                System.out.println("Invalid Car id");
                            }
                        }

                        break;

                    /*
                     * return functionalities
                     * ask cust id and car id => check in rental table 
                     * select id where cust id =  x && car id=y 
                     * toggle availability using input car id(func in car class)
                     
                     * 6. calculate the total cost by subtracting the date from current date
                     * 7. return date must be current timestamp and also update the cost in rental
                     * table
                     */
                    case 3:
                        System.out.println("Enter the car ID: ");
                        int carID = scan.nextInt();
                        System.out.println("Enter the customer ID: ");
                        int custID = scan.nextInt();

                        int transactionID = Rental.checkValidTransaction(carID, custID, connection);
                        if(transactionID==0){
                            System.out.println("No any renting  record found for this car or customer.");
                            break;
                        }
                        Rental.returnCar(transactionID, connection);
                        System.out.println("successful return");
                        Car.changeAvailability(carID, connection);
                        break;

                    // showing rental history (inner join of all tables)
                    case 4:
                        Rental.showRentalHistory(connection);
                        break;

                    // search for a spicific car using make
                    case 5:
                        System.out.println("Enter the desired make: ");
                        String make = scan.next();
                        Car.searchByMake(make, connection);
                        break;

                    case 0:

                        isRunning = false;
                        break;

                    default:
                        System.out.println("Invalid  Choice! Please try again.");
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        } finally {
            // Close any resources (e.g., database connection) in the finally block
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing database connection: " + e.getMessage());
            }
            scan.close(); // Close scanner to prevent resource leak
        }

    }
}
