import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Database implements IDatabase {
    
    //Database URL
//    private final static String DB_URL = "jdbc:mysql://swe2.cs.hm.edu:21964/sap_emulation";
    private final static String DB_URL = "jdbc:mysql://10.28.2.166:3306/sap_emulation";
    
    //Database credentials
    private final static String USER = "ExtDev2";
//    private final static String PASSWORD = "2N682Gsa";
    
    /**Connection to the database.*/
    private Connection connection;
    
    private ArrayList<Supplier> data = new ArrayList<Supplier>();
    
    /**Empty constructor. Not needed here.*/
    public Database() {}
    

    @Override
    public void establishConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, decryptPassword());
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
    
    private void extractData() {
    	for(String supplier : getSuppliers()) {
    		data.add(new Supplier(supplier));
    	}
    	
    	try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT lfa1.name1, eket.ebeln, ebelp, eindt, slfdt FROM lfa1, eket, ekko WHERE ekko.lifnr = lfa1.lifnr AND ekko.ebeln = eket.ebeln");
            
            while(set.next()) {
            	
            	for(Supplier supplier : data) {
            		if(set.getString("name1").equals(supplier.getSupplier())) {
            			supplier.addDelivery(new Delivery(set.getString("ebeln"), set.getInt("ebelp"), set.getString("eindt"), set.getString("slfdt")));
            		}
            	}
            }
            
    	}	catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private String decryptPassword() {
		byte[] keyBytes = new byte[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,37, 41, 43, 47, 53 };
		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

		String test = "þÖ±¡üŒB³YnÿÝ!e„";
		String password = "";

		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);

			byte[] decryptedPwd = cipher.doFinal(test.getBytes());
			password = new String(decryptedPwd);
		} catch (Exception e) {

		}
		return password;
    }

    public static void main(String[] args) {
    	Database db = new Database();
        db.establishConnection();
        db.extractData();
        
        for(Supplier s : db.data) {
        	System.out.println(s.getSupplier());
        	System.out.println("Anzahl Lieferungen :" + s.getDeliveries().size());
        	
        	for(Delivery d : s.getDeliveries()) {
        		System.out.println("EBELN : " + d.getEBELN() + " EBELP : " + d.getEBELP() + " EINDT : " + d.getEINDT() + " SLFDT : " + d.getSLFDT());
        	}
        	
        	System.out.println("#########################");
        }
        
        db.closeConnection();
    }
}
