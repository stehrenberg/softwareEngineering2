/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Kevin Beck
Date: 16-11-2015
*/
package edu.hm.cs.softengii.db.dataStorage;

import java.util.ArrayList;

/**
 * Interface to manage storing data in the database
 * @author Kevin Beck
 */
public interface IDatabaseDataStorage {

    /**
     * Establish connection to the database
     */
    void establishConnection();

    /**
     * Close connection to the database
     */
    void closeConnection();
    
    /**
     * Get all score thresholds from the database
     */
    ArrayList<ScoreThresholdEntity> getScoreThresholds();
    
    /**
     * Set new score thresholds to the database. Old thresholds are removed.
     * @param thresholds New thresholds
     */
    void setScoreThresholds(ArrayList<ScoreThresholdEntity> thresholds);
}