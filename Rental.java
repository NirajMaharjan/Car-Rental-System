import java.util.Scanner;
import java.sql.Connection;

public class Rental {

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

            switch (ch) {
                case 1:
                    try{
                        connection = ConnectDB.getConnection();
                        Car.viewAvailableCars(connection);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                
                case 0:
                    scan.close();
                    System.exit(1);
                default:
                    System.out.println("Invalid  Choice! Please try again.");
                    break;
            }

        }while(true);

    }   
}



