/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Kevin Beck
Date: 13-12-2015
*/

package edu.hm.cs.softengii.db.dataStorage;

/**
 * Class to represent a delivery range threshold entity
 * @author Kevin Beck
 */
public class DeliveryRangeThresholdEntity {

	
	
	// Fields ---------------------------------------------------------------------------
	
	/** Delivery range name */
    private DeliveryRange deliveryRangeName;
    
    /** Minimum value of days */
    private int daysMin;
    
    /** Maximum value of days */
    private int daysMax;

    // Ctor -----------------------------------------------------------------------------

    /**
     * Create a new instance
     * @param deliveryRangeName Name for this range threshold
     * @param daysMin Minimum value of days
     * @param daysMax Maximum value of days
     */
    public DeliveryRangeThresholdEntity(DeliveryRange deliveryRangeName, int daysMin, int daysMax) {
    	
        this.deliveryRangeName = deliveryRangeName;
        this.daysMin = daysMin;
        this.daysMax = daysMax;
    }

    // Getter ---------------------------------------------------------------------------
    
    /**
     * Get name of delivery range
     * @return Name of delivery range
     */
    public DeliveryRange getDeliveryRangeName() {
        return this.deliveryRangeName;
    }
    
    /**
     * Get minimum value of days
     * @return Minimum value of days
     */
    public int getDaysMin() {
        return this.daysMin;
    }
    
    /**
     * Get maximum value of days
     * @return Maximum value of days
     */
    public int getDaysMax() {
        return this.daysMax;
    }
    
    // Setter ---------------------------------------------------------------------------

    /**
     * Set the name of this delivery range
     * @param deliveryRangeName Name of this delivery range
     */
    public void setDeliveryRangeName(DeliveryRange deliveryRangeName) {
        this.deliveryRangeName = deliveryRangeName;
    }

    /**
     * Set the minimum value of days
     * @param earlyMin Minimum value of days
     */
    public void setDaysMin(int daysMin) {
        this.daysMin = daysMin;
    }

    /**
     * Set the maximum value of days
     * @param earlyMax Maximum value of days
     */
    public void setDaysMax(int daysMax) {
        this.daysMax = daysMax;
    }
}
