import java.util.Scanner;

public class Rental {

    //Menu lists
    private void menu(){
        System.out.println("Welcome to the Car Rental System!");
        System.out.println("Please enter your choice:");
        System.out.println("1. View available cars");
        System.out.println("2. Rent a car");
        System.out.println("3. Return a car");
        System.out.println("4. View Rental history");
        System.out.println("5. Search for a car ");
        System.out.println("6. Exit");
    }

    //main function
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Rental obj = new Rental();
        
        do{
            obj.menu();
            int ch = scan.nextInt();

            switch (ch) {
                case 1:
                    
                    break;
            
                default:
                    break;
            }

        }while(true);

    }   
}
