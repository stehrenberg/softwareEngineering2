/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Kevin Beck
Date: 16-11-2015
*/
package edu.hm.cs.softengii.db.dataStorage;

import edu.hm.cs.softengii.utils.SettingsPropertiesHelper;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

/**
 * Wrapper class to manage storing data in the database
 * @author Kevin Beck
 */
public class DatabaseDataStorage implements IDatabaseDataStorage {

	// Fields ---------------------------------------------------------------------------
	
	/** Singleton instance of this class */
    private static DatabaseDataStorage instance = null;

    /** Database URL */
	private final static String DB_URL = "jdbc:sqlite::resource:" +
			DatabaseDataStorage.class.getResource("dataStorage.db").toString();

    /** Database username */
    private final static String USER = SettingsPropertiesHelper.getInstance().getDataStorageDbUser();
    
    /** Database password */
    private final static String PASSWORD = SettingsPropertiesHelper.getInstance().getDataStorageDbPswd();

    /** Connection to the database */
    private Connection connection;

    // Ctor -----------------------------------------------------------------------------
    
    /** 
     * Create a new instance
     */
    private DatabaseDataStorage() {
        this.establishConnection();
    }

    // Static methods -------------------------------------------------------------------

    /**
     * Get the singleton instance of this class
     * @return Instance of this class
     */
    public static DatabaseDataStorage getInstance() {
    	
        if (instance == null) {
            instance = new DatabaseDataStorage();
        }
        
        return instance;
    }

    // Public methods -------------------------------------------------------------------
    
    /**
     * Establish a connection to the database
     */
    @Override
    public void establishConnection() {
    	
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close connection to the database
     */
    @Override
    public void closeConnection() {
    	
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get all score thresholds from the database
     */
    @Override
    public ArrayList<ScoreThresholdEntity> getScoreThresholds() {
    	
    	ArrayList<ScoreThresholdEntity> thresholds = new ArrayList<>();
    	
    	establishConnection();
    	
		try {
		    String query = "SELECT * FROM ScoreThresholds;";
		    Statement statement = this.connection.createStatement();
		    ResultSet set = statement.executeQuery(query);
		    
		    while (set.next()) {
		    	
		    	int scoreValue = set.getInt(1);
		    	int earlyMin = set.getInt(2);
		    	int earlyMax = set.getInt(3);
		    	int lateMin = set.getInt(4);
		    	int lateMax = set.getInt(5);
		    	
		    	thresholds.add(new ScoreThresholdEntity(scoreValue, earlyMin, earlyMax, lateMin, lateMax));
		    }
		
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		closeConnection();
		
		return thresholds;
    }

    /**
     * Get all default score thresholds from the database
     */
    @Override
    public ArrayList<ScoreThresholdEntity> getScoreDefaults() {

    	ArrayList<ScoreThresholdEntity> thresholds = new ArrayList<>();

    	establishConnection();

		try {
		    String query = "SELECT * FROM ScoreDefaults;";
		    Statement statement = this.connection.createStatement();
		    ResultSet set = statement.executeQuery(query);

		    while (set.next()) {

		    	int scoreValue = set.getInt(1);
		    	int earlyMin = set.getInt(2);
		    	int earlyMax = set.getInt(3);
		    	int lateMin = set.getInt(4);
		    	int lateMax = set.getInt(5);

		    	thresholds.add(new ScoreThresholdEntity(scoreValue, earlyMin, earlyMax, lateMin, lateMax));
		    }

		} catch (SQLException e) {
		    e.printStackTrace();
		}

		closeConnection();

		return thresholds;
    }
    
    /**
     * Set new score thresholds to the database. Old thresholds are removed.
     * @param thresholds New thresholds
     */
    @Override
    public void setScoreThresholds(ObservableList<ScoreThresholdEntity> thresholds) {
    	
    	establishConnection();
    	
		try {
			// First remove all old thresholds in db
		    String query = "DELETE FROM ScoreThresholds;";
		    Statement statement = this.connection.createStatement();
		    statement.execute(query);
		    
		    // Then insert new thresholds to database
		    for (ScoreThresholdEntity threshold: thresholds) {
		    	
		    	query = "INSERT INTO ScoreThresholds VALUES (" +
		    			threshold.getScoreValue() + ", " + 
		    			threshold.getEarlyMin() + ", " + 
		    			threshold.getEarlyMax() + ", " + 
		    			threshold.getLateMin() + ", " + 
		    			threshold.getLateMax() + ");";
			    statement = this.connection.createStatement();
			    statement.execute(query);
		    }
		
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		closeConnection();
    }
    
    /**
     * Get all delivery range thresholds from the database
     */
    @Override
    public ArrayList<DeliveryRangeThresholdEntity> getDeliveryRangeThresholds() {
    	
    	ArrayList<DeliveryRangeThresholdEntity> thresholds = new ArrayList<>();
    	
    	establishConnection();
    	
		try {
		    String query = "SELECT * FROM DeliveryRangeThresholds;";
		    Statement statement = this.connection.createStatement();
		    ResultSet set = statement.executeQuery(query);
		    
		    while (set.next()) {
		    	
		    	DeliveryRange deliveryRange = DeliveryRange.values()[set.getInt(1)];
		    	int daysMin = set.getInt(2);
		    	int daysMax = set.getInt(3);
		    	
		    	thresholds.add(new DeliveryRangeThresholdEntity(deliveryRange, daysMin, daysMax));
		    }
		
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		closeConnection();
		
		return thresholds;
    }

    /**
     * Get all default delivery range thresholds from the database
     */
    @Override
    public ArrayList<DeliveryRangeThresholdEntity> getDeliveryRangeDefaults() {

    	ArrayList<DeliveryRangeThresholdEntity> thresholds = new ArrayList<>();
    	
    	establishConnection();
    	
		try {
		    String query = "SELECT * FROM DeliveryRangeDefaults;";
		    Statement statement = this.connection.createStatement();
		    ResultSet set = statement.executeQuery(query);
		    
		    while (set.next()) {
		    	
		    	DeliveryRange deliveryRange = DeliveryRange.values()[set.getInt(1)];
		    	int daysMin = set.getInt(2);
		    	int daysMax = set.getInt(3);
		    	
		    	thresholds.add(new DeliveryRangeThresholdEntity(deliveryRange, daysMin, daysMax));
		    }
		
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		closeConnection();
		
		return thresholds;
    }
    
    /**
     * Set new delivery range thresholds to the database. Old thresholds are removed.
     * @param thresholds New thresholds
     */
    @Override
    public void setDeliveryRangeThresholds(ObservableList<DeliveryRangeThresholdEntity> thresholds) {
    	
    	establishConnection();
    	
		try {
			// First remove all old thresholds in db
		    String query = "DELETE FROM DeliveryRangeThresholds;";
		    Statement statement = this.connection.createStatement();
		    statement.execute(query);
		    
		    // Then insert new thresholds to database
		    for (DeliveryRangeThresholdEntity threshold: thresholds) {
		    	
		    	query = "INSERT INTO DeliveryRangeThresholds VALUES (" +
		    			threshold.getDeliveryRangeName().ordinal() + ", " + 
		    			threshold.getDaysMin() + ", " + 
		    			threshold.getDaysMax() + ");";
			    statement = this.connection.createStatement();
			    statement.execute(query);
		    }
		
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		closeConnection();
    }
    
    /**
     * Get all supplier classification thresholds from the database
     */
    @Override
    public ArrayList<SupplierClassificationThresholdEntity> getSupplierClassificationThresholds() {
    	
    	ArrayList<SupplierClassificationThresholdEntity> thresholds = new ArrayList<>();
    	
    	establishConnection();
    	
		try {
		    String query = "SELECT * FROM SupplierClassificationThresholds;";
		    Statement statement = this.connection.createStatement();
		    ResultSet set = statement.executeQuery(query);
		    
		    while (set.next()) {
		    	
		    	SupplierClass supplierClass = SupplierClass.values()[set.getInt(1)];
		    	int deliveriesMin = set.getInt(2);
		    	int deliveriesMax = set.getInt(3);
		    	
		    	thresholds.add(new SupplierClassificationThresholdEntity(supplierClass, deliveriesMin, deliveriesMax));
		    }
		
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		closeConnection();
		return thresholds;
    }

    /**
     * Get all default supplier classification thresholds from the database
     */
    @Override
    public ArrayList<SupplierClassificationThresholdEntity> getSupplierClassificationDefaults() {

        ArrayList<SupplierClassificationThresholdEntity> thresholds = new ArrayList<>();
    	
    	establishConnection();
    	
		try {
		    String query = "SELECT * FROM SupplierClassificationDefaults;";
		    Statement statement = this.connection.createStatement();
		    ResultSet set = statement.executeQuery(query);
		    
		    while (set.next()) {
		    	
		    	SupplierClass supplierClass = SupplierClass.values()[set.getInt(1)];
		    	int deliveriesMin = set.getInt(2);
		    	int deliveriesMax = set.getInt(3);
		    	
		    	thresholds.add(new SupplierClassificationThresholdEntity(supplierClass, deliveriesMin, deliveriesMax));
		    }
		
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		closeConnection();
		
		return thresholds;
    }
    
    /**
     * Set new supplier classification thresholds to the database. Old thresholds are removed.
     * @param thresholds New thresholds
     */
    @Override
    public void setSupplierClassificationThresholds(ObservableList<SupplierClassificationThresholdEntity> thresholds) {
    	
    	establishConnection();

		try {
			// First remove all old thresholds in db
		    String query = "DELETE FROM SupplierClassificationThresholds;";
		    Statement statement = this.connection.createStatement();
		    statement.execute(query);

		    // Then insert new thresholds to database
		    for (SupplierClassificationThresholdEntity threshold : thresholds) {

		    	query = "INSERT INTO SupplierClassificationThresholds VALUES (" +
		    			threshold.getClassName().ordinal() + ", " +
		    			threshold.getDeliveriesMin() + ", " +
		    			threshold.getDeliveriesMax() + ");";

                statement = this.connection.createStatement();
			    statement.execute(query);
		    }

		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		closeConnection();
    }
}