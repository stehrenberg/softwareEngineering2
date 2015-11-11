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
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    private ArrayList<Supplier> suppliers;

    private Database() {
        Runnable dataLoader = new Runnable() {
            @Override public void run() {
                loadSupplierData();
            }
        };
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

    public Map<String, String> getSupplierList() {

        Map<String, String> suppliers = new HashMap<>();
        establishConnection();

        try {
            String query = String.format("SELECT * FROM lfa1");

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {
                String supplierID = set.getString("lifnr");
                String name = set.getString("name1");
                System.out.println(supplierID + "/" + name);
                suppliers.put(supplierID, name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return suppliers;
    }

    @Override
    public ArrayList<Supplier> getSupplierData() {
        loadSupplierData();
        return supplierData;
    }

    private void loadSupplierData() {

    	establishConnection();

        ArrayList<Supplier> suppliers = new ArrayList<>();

        try {

            String query = String.format(
                    "SELECT l.lifnr, name1, eindt, slfdt, m.ebeln "
                            + "FROM lfa1 l, eket m, ekko n "
                            + "WHERE m.ebeln = n.ebeln AND l.lifnr = n.lifnr LIMIT 10"
            );

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

/*            String deliveryDelaysQuery = "SELECT ekbe.EBELN, BUDAT, SLFDT, VGABE, DATEDIFF(BUDAT, SLFDT) AS DIFF " +
                    "FROM ekbe ekbe, eket eket " +
                    "WHERE ekbe.EBELN = eket.EBELN " +
                    "AND ekbe.VGABE = 1 " +
                    "GROUP BY ekbe.EBELN;";
            ResultSet deliveriesWithDelay = connection.createStatement().executeQuery(deliveryDelaysQuery);*/

            while(set.next()) {

            	Supplier supplier = findSupplierInList(suppliers, set.getString("l.lifnr"));
            	if (supplier == null) {
            		supplier = new Supplier(set.getString("l.lifnr"), set.getString("name1"));
            		suppliers.add(supplier);
            	}
                LocalDate eindt = set.getDate("eindt").toLocalDate();
                LocalDate slfdt = set.getDate("slfdt").toLocalDate();
                System.out.println("eindt / slfdt: " + eindt + "/" + slfdt);

            	Delivery delivery = new Delivery(
                        set.getDate("eindt").toLocalDate(),
                        set.getDate("slfdt").toLocalDate());
                //delivery.setDelay(set.getInt("DIFF"));
            	supplier.getDeliveries().add(delivery);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.suppliers = suppliers;
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

    public List<Delivery> filterDeliveriesByDate(String supplierID, LocalDate rangeStart, LocalDate rangeEnd) {
        Supplier supplier = getSupplierByID(supplierID);
        List<Delivery> filteredDels = supplier.getDeliveries().stream()
                .filter(delivery -> delivery.getActualDeliveryDate().isAfter(rangeStart))
                .collect(Collectors.toList());
        filteredDels.stream().forEach(del -> System.out.println("del is: " + del + "isAfter(" + rangeStart + "): " + del.getActualDeliveryDate().isAfter(rangeStart)));

        return filteredDels;
    }

    private Supplier getSupplierByID(String supplierID) {
        return findSupplierInList(suppliers, supplierID);
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
