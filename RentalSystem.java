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
                    case 1:
                            
                         Car.viewAvailableCars(connection);
                        break;
                    
                    case 2:
                        Customer.askDetails(scan, connection);
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



