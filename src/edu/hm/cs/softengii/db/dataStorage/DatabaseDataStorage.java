/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Kevin Beck
Date: 16-11-2015
*/
package edu.hm.cs.softengii.db.dataStorage;

import edu.hm.cs.softengii.utils.SettingsPropertiesHelper;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class to manage storing data in the database
 * @author Kevin Beck
 */
public class DatabaseDataStorage implements IDatabaseDataStorage {

	// Fields ---------------------------------------------------------------------------
	
	/** Singleton instance of this class */
    private static DatabaseDataStorage instance = null;

    /** Database URL */
    private final static String DB_URL = SettingsPropertiesHelper.getInstance().getDataStorageDbUrl();

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
            if(this.connection == null || this.connection.isClosed()) {
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
		    String query = String.format("SELECT * FROM ScoreThresholds;");
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
}