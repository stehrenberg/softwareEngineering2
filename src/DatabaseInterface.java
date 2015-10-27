import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Database {
    
    //Database URL
    private final static String DB_URL = "jdbc:mysql://swe2.cs.hm.edu:21964/sap_emulation";
    
    //Database credentials
    private final static String USER = "ExtDev2";
    private final static String PASSWORD = "2N682Gsa";
    
    /**Connection to the database.*/
    private Connection connection;
    
    /**Empty constructor*/
    public DatabaseInterface() {}
    
    /**Opens the connection to the database.*/
    public void establishConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**Close the connection to the database.*/
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //GetSuppliers soll zur Auswahl der einzelnen Supplier im Drop Down Menü verwendet werden.
    
    /**Finds all suppliers listed in the database and collects them in an ArrayList.
     * @return All suppliers in database.
     */
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
    
    //Soll die Daten für einen gewissen Zeitraum liefern, falls in der GUI die Einstellungen angepasst werden.
    
    public void getDataForDateRange(final Date startDate, final Date endDate) {
        //Should return the data for this range.
    }

    public static void main(String[] args) {
    	Database db = new Database();
        db.establishConnection();
        db.getSuppliers();
    }
}
