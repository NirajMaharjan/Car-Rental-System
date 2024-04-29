import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;


public class Customer {
    private int id;
    private String name;
    private int age;
    private long contact;
    private long liscenseNumber;



    public static int askDetails(Scanner scan,Connection connection){
        Customer customer = new Customer();
        System.out.println("Enter customer liscense number: ");
        customer.liscenseNumber=scan.nextLong();
        scan.nextLine();

        int existingCustomer = checkExistingCustomer(customer.liscenseNumber,connection);
        if(existingCustomer != -1){
            System.out.println("Welcome Back!!");
            return existingCustomer;
        }

        System.out.println("Enter customer name: ");
        customer.name=scan.nextLine();

        System.out.println("Enter customer age: ");
        customer.age=scan.nextInt();
        scan.nextLine();
        
        System.out.println("Enter customer contact: ");
        customer.contact=scan.nextLong();
        scan.nextLine();
        return registerCustomer(connection, customer);
        
    }


    //this method is used for registering the user in the database and is used in the askDetails method
    public static int registerCustomer(Connection connection,Customer customer){
        String query = "insert into customer(name,age,contact,liscense_number) values(?,?,?,?) ";
        int generatedCustomerId = -1;

        try{

            PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.name);
            preparedStatement.setInt(2, customer.age);
            preparedStatement.setLong(3, customer.contact);
            preparedStatement.setLong(4, customer.liscenseNumber);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0){
                System.out.println("Customer registered Successfully!!!");
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next()){
                    generatedCustomerId = rs.getInt(1);
                }
  
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return generatedCustomerId;
        
    }


    //this method check if there is the existing customer with the same liscense number so that there no duplictae liscense number
    public static int checkExistingCustomer(long licenseNum,Connection connection) {

        String sql = "SELECT * FROM customer WHERE liscense_number = ?";
        try {
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, licenseNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return  resultSet.getInt("customer_id");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
