package edu.hm.cs.softengii.db.sap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database implements IDatabase {

    private static Database instance = null;

    //Database URL
    private final static String DB_URL = "jdbc:mysql://swe2.cs.hm.edu:21964/sap_emulation";

    //Database credentials
    private final static String USER = "ExtDev2";
    private final static String PASSWORD = "2N682Gsa";

    /**Connection to the database.*/
    private Connection connection;

    /**Empty constructor. Not needed here.*/
    private Database() {
        establishConnection();
    }


    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    @Override
    public void establishConnection() {
        try {
            if(connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

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
            		supplier = new Supplier(set.getString("l.lifnr"), set.getString("name1"), new ArrayList<>());
            		suppliers.add(supplier);
            	}
            	
            	Delivery delivery = new Delivery(set.getDate("eindt"), set.getDate("slfdt"));
            	supplier.getDeliveries().add(delivery);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        closeConnection();
        
        return suppliers;
    }
    
    private static Supplier findSupplierInList(ArrayList<Supplier> suppliers, String id) {
    	for (Supplier supplier: suppliers) {
    		if (supplier.getId().equals(id)) {
    			return supplier;
    		}
    	}
    	
    	return null;
    }
}