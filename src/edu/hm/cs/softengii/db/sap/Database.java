package edu.hm.cs.softengii.db.sap;

import edu.hm.cs.softengii.utils.SettingsPropertiesHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database implements IDatabase {

    private static Database instance = null;

    //Database URL
    private final static String DB_URL = SettingsPropertiesHelper.getInstance().getSapDbUrl();

    //Database credentials
    private final static String USER = SettingsPropertiesHelper.getInstance().getSapDbUser();
    private final static String PASSWORD = SettingsPropertiesHelper.getInstance().getSapDbPswd();

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
        ArrayList<Supplier> supplierData = new ArrayList<>();

        return supplierData;
    }
}