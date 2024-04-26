import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Check {

    public static void main(String[] args) {
        String sql = "Select * from customer";
        try{
            Connection connection = ConnectDB.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String namme = rs.getString("name");
                System.out.println(namme);
            }
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }


    }
}
