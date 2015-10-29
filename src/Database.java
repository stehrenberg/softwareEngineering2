import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database implements IDatabase {
    
    //Database URL
    private final static String DB_URL = "jdbc:mysql://swe2.cs.hm.edu:21964/sap_emulation";
    
    //Database credentials
    private final static String USER = "ExtDev2";
    private final static String PASSWORD = "2N682Gsa";
    
    /**Connection to the database.*/
    private Connection connection;
    
    /**Empty constructor. Not needed here.*/
    public Database() {}
    

    @Override
    public void establishConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public ArrayList<String> getSuppliers() {
        ArrayList<String> suppliers = new ArrayList<>();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("Select * from lfa1");
            
            while(set.next()) {
                System.out.println(set.getString("name1"));
                suppliers.add(set.getString("name1"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return suppliers;
    }
    
    @Override
    public ArrayList<Supplier> getSupplierData() {
        ArrayList<Supplier> supplierData = new ArrayList<>();
        
        return supplierData;
    }

    public static void main(String[] args) {
        IDatabase db = new Database();
        db.establishConnection();
        db.getSuppliers();
    }
}
