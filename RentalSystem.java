import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;


public class RentalSystem {

    //Menu lists
    private static void  menuList() {
        System.out.println("Welcome to the Car Rental System!");
        System.out.println("Please enter your choice:");
        System.out.println("1. View available cars");
        System.out.println("2. Rent a car");
        System.out.println("3. Return a car");
        System.out.println("4. View Rental history");
        System.out.println("5. Search for a car ");
        System.out.println("0. Exit");
    }

    //main function
    public static void main(String[] args) {
        Connection connection = null;

        
        do{
            Scanner scan = new Scanner(System.in);
            menuList();
            int ch = scan.nextInt();
            try{
                connection = ConnectDB.getConnection();
                switch (ch) {

                    //view all the available cars only
                    case 1:
                            
                         Car.viewAvailableCars(connection);
                        break;
                    
                    /*
                     * car renting process
                     * asks the customer liscense number -> if not existing customer then ask all the ddetails
                     * gets the car id and customer id
                     * change the availability of corresponding car id to false
                     * records the rent in rental table
                     */
                    case 2:
                        int customer_ID=Customer.askDetails(scan, connection);
                        if(customer_ID!= -1){
                            System.out.println("Enter the Car ID: ");
                            int car_ID = scan.nextInt();
                            if(Car.isValidCarId(car_ID,connection) && Car.checkAvailability(car_ID,connection)){
                                Car.changeAvailability(car_ID, connection);
                                Rental.rentCar(customer_ID,car_ID,connection);

                            }
                            else{
                                boolean check=Car.checkAvailability(car_ID, connection);  
                                System.out.println(check);                    
                                System.out.println("Invalid Car id");
                            }
                        }
                        
                        break;
                    
                    case 0:
                        scan.close();
                        System.exit(1);
                    
                    default:
                        System.out.println("Invalid  Choice! Please try again.");
                        break;
                }
    
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            
        }while(true);

    }   
}




