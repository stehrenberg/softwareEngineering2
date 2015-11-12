package edu.hm.cs.softengii.db.sap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;

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
    private List<Supplier> suppliers;

    private Database() {

    	Runnable dataLoader = () -> loadSupplierData();
		new Thread(dataLoader).start();
    }

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
                connection = DriverManager.getConnection(DB_URL, USER, "2N682Gsa");//decryptPassword(PASSWORD));

                System.out.println("connection esstablished");
                System.out.println("url: " + DB_URL);
                System.out.println("user: " + USER);
                System.out.println("pswd: " + PASSWORD);
            }
        } catch (SQLException e) {
        	Application.launch(ErrorMessage.class, ErrorMessage.convertExceptionToString(e));
        }
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
        	Application.launch(ErrorMessage.class, ErrorMessage.convertExceptionToString(e));
        }
    }

    @Override
    public ArrayList<String> getSuppliers() {

        establishConnection();

        ArrayList<String> suppliers = new ArrayList<>();

        try {

            String query = "SELECT * FROM lfa1";

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {
                System.out.println(set.getString("name1"));
                suppliers.add(set.getString("name1"));
            }

        } catch (SQLException e) {
        	Application.launch(ErrorMessage.class, ErrorMessage.convertExceptionToString(e));
        }

        closeConnection();

        return suppliers;
    }

    /**
     * Idea for a function getting a list of suppliers with their respective ID (== lifnr from DB)
     * to show within application when a supplier is to be picted.
     * @return
     */
    public Map<String, String> getSupplierList() {

        Map<String, String> suppliers = new HashMap<>();
        establishConnection();

        try {
            String query = "SELECT * FROM lfa1";

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {
                String supplierID = set.getString("lifnr");
                String name = set.getString("name1");
                System.out.println(supplierID + "/" + name);
                suppliers.put(supplierID, name);
            }

        } catch (SQLException e) {
        	Application.launch(ErrorMessage.class, ErrorMessage.convertExceptionToString(e));
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

            String query = "SELECT DISTINCT "
                    + "eket.EBELN, SLFDT, BUDAT, lfa1.LIFNR, NAME1, DATEDIFF(BUDAT, SLFDT) AS DIFF "
                    + "FROM eket, ekbe, ekko, lfa1 "
                    + "WHERE ekko.EBELN = ekbe.EBELN "
                    + "AND eket.ebeln = ekbe.ebeln "
                    + "AND lfa1.lifnr = ekko.lifnr "
                    + "AND VGABE = 1 LIMIT 100";

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

            	Supplier supplier = findSupplierInList(suppliers, set.getString("lfa1.LIFNR"));
            	if (supplier == null) {
            		supplier = new Supplier(set.getString("lfa1.LIFNR"), set.getString("NAME1"));
            		suppliers.add(supplier);
            	}
                LocalDate actual = set.getDate("BUDAT").toLocalDate();
                LocalDate promised = set.getDate("SLFDT").toLocalDate();
                String delID = set.getString("eket.EBELN");
                System.out.println("budat / slfdt: " + actual + "/" + promised);

            	Delivery delivery = new Delivery(delID, promised, actual);
                delivery.setDelay(set.getInt("DIFF"));
            	supplier.getDeliveries().add(delivery);
            }

        } catch (SQLException e) {
        	javafx.application.Application.launch(ErrorMessage.class, ErrorMessage.convertExceptionToString(e));
        }

        this.suppliers = suppliers;
        supplierData = suppliers;

        closeConnection();
    }

    private static Supplier findSupplierInList(List<Supplier> suppliers, String id) {
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
		} catch (Exception e) {}
		return password;
    }
}
