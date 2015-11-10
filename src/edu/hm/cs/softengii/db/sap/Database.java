package edu.hm.cs.softengii.db.sap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import edu.hm.cs.softengii.utils.SettingsPropertiesHelper;

public class Database implements IDatabase {

    private static Database instance = null;

    //Database URL
    private final static String DB_URL = SettingsPropertiesHelper.getInstance().getSapDbUrl();

    //Database credentials
    private final static String USER = SettingsPropertiesHelper.getInstance().getSapDbUser();
    private final static String PASSWORD = SettingsPropertiesHelper.getInstance().getSapDbPswd();

    /**Connection to the database.*/
    private Connection connection;

    private ArrayList<Supplier> supplierData = new ArrayList<>();
    
    public static Database getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    public static void createInstance() {
    	instance = new Database();
    }

    @Override
    public void establishConnection() {
        try {
            if(connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USER, decryptPassword(PASSWORD));

                System.out.println("connection esstablished");
                System.out.println("url: " + DB_URL);
                System.out.println("user: " + USER);
                System.out.println("pswd: " + PASSWORD);
            }
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

        establishConnection();

        ArrayList<String> suppliers = new ArrayList<>();

        try {

            String query = String.format("SELECT * FROM lfa1");

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {
                System.out.println(set.getString("name1"));
                suppliers.add(set.getString("name1"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return suppliers;
    }

    @Override
    public ArrayList<Supplier> getSupplierData() {
    	
        return supplierData;
    }
    
    private void loadSupplierData() {

    	establishConnection();
    	
        ArrayList<Supplier> suppliers = new ArrayList<>();

        try {

            String query = String.format(
            		"SELECT l.lifnr, name1, eindt, slfdt "
            		+ "FROM lfa1 l, eket m, ekko n "
            		+ "WHERE m.ebeln = n.ebeln AND l.lifnr = n.lifnr"
        		);

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {
            	
            	Supplier supplier = findSupplierInList(suppliers, set.getString("l.lifnr"));
            	if (supplier == null) {
            		supplier = new Supplier(set.getString("l.lifnr"), set.getString("name1"));
            		suppliers.add(supplier);
            	}
            	
            	Delivery delivery = new Delivery(set.getDate("eindt"), set.getDate("slfdt"));
            	supplier.getDeliveries().add(delivery);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        supplierData = suppliers;
        
        closeConnection();
    }
    
    private static Supplier findSupplierInList(ArrayList<Supplier> suppliers, String id) {
    	for (Supplier supplier: suppliers) {
    		if (supplier.getId().equals(id)) {
    			return supplier;
    		}
    	}
    	
    	return null;
    }
    
    private String decryptPassword(final String cryptedPassword) {
		byte[] keyBytes = new byte[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,37, 41, 43, 47, 53 };
		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

		String password = "";

		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);

			byte[] decryptedPwd = cipher.doFinal(cryptedPassword.getBytes());
			password = new String(decryptedPwd);
		} catch (Exception e) {

		}
		return password;
    }
}
