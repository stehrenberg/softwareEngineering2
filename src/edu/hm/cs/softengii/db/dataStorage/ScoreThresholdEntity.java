/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Kevin Beck
Date: 16-11-2015
*/

package edu.hm.cs.softengii.db.dataStorage;

/**
 * Class to represent a score threshold database entity
 * @author Kevin Beck
 */
public class ScoreThresholdEntity {

	// Fields ---------------------------------------------------------------------------
	
	/** Score value in percent of this threshold */
    private int scoreValue;
    
    /** Minimum value for too early deliveries */
    private int earlyMin;
    
    /** Maximum value for too early deliveries */
    private int earlyMax;
    
    /** Minimum value for too late deliveries */
    private int lateMin;
    
    /** Maximum value for too late deliveries */
    private int lateMax;

    // Ctor -----------------------------------------------------------------------------

    /**
     * Create a new instance
     * @param scoreValue Score value in percent of this threshold
     * @param earlyMin Minimum value for too early deliveries
     * @param earlyMax Maximum value for too early deliveries
     * @param lateMin Minimum value for too late deliveries
     * @param lateMax Maximum value for too late deliveries
     */
    public ScoreThresholdEntity(int scoreValue, int earlyMin, int earlyMax, int lateMin, int lateMax) {
    	
        this.scoreValue = scoreValue;
        this.earlyMin = earlyMin;
        this.earlyMax = earlyMax;
        this.lateMin = lateMin;
        this.lateMax = lateMax;
    }

    // Getter ---------------------------------------------------------------------------
    
    /**
     * Get score value in percent of this threshold
     * @return Score value in percent
     */
    public int getScoreValue() {
        return this.scoreValue;
    }
    
    /**
     * Get minimum value for too early deliveries
     * @return Minimum value for too early deliveries
     */
    public int getEarlyMin() {
        return this.earlyMin;
    }
    
    /**
     * Get maximum value for too early deliveries
     * @return Maximum value for too early deliveries
     */
    public int getEarlyMax() {
        return this.earlyMax;
    }
    
    /**
     * Get minimum value for too late deliveries
     * @return Minimum value for too late deliveries
     */
    public int getLateMin() {
        return this.lateMin;
    }
    
    /**
     * Get maximum value for too late deliveries
     * @return Maximum value for too late deliveries
     */
    public int getLateMax() {
        return this.lateMax;
    }
}
