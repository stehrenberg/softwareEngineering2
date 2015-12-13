/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Kevin Beck
Date: 13-12-2015
*/

package edu.hm.cs.softengii.db.dataStorage;

/**
 * Class to represent a supplier classification threshold entity
 * @author Kevin Beck
 */
public class SupplierClassificationThresholdEntity {

	// Fields ---------------------------------------------------------------------------
	
	/** Classification name */
    private SupplierClass className;
    
    /** Minimum value of deliveries */
    private int deliveriesMin;
    
    /** Maximum value of deliveries */
    private int deliveriesMax;

    // Ctor -----------------------------------------------------------------------------

    /**
     * Create a new instance
     * @param className Classification name
     * @param delivieriesMin Minimum value of deliveries
     * @param delivieriesMax Maximum value of delivieries
     */
    public SupplierClassificationThresholdEntity(SupplierClass className, int deliveriesMin, int deliviriesMax) {
    	
        this.className = className;
        this.deliveriesMin = deliveriesMin;
        this.deliveriesMax = deliviriesMax;
    }

    // Getter ---------------------------------------------------------------------------
    
    /**
     * Get classification name
     * @return Classification name
     */
    public SupplierClass getClassificationName() {
        return this.className;
    }
    
    /**
     * Get minimum value of deliveries
     * @return Minimum value of deliveries
     */
    public int getDelivieriesMin() {
        return this.deliveriesMin;
    }
    
    /**
     * Get maximum value of deliveries
     * @return Maximum value of deliveries
     */
    public int getDelivieriesMax() {
        return this.deliveriesMax;
    }

    // Setter ---------------------------------------------------------------------------

    /**
     * Set the classfication name
     * @param className Classification name
     */
    public void setClassificationName(SupplierClass className) {
        this.className = className;
    }

    /**
     * Set the minimum value of deliveries
     * @param deliveriesMin Minimum value of deliveries
     */
    public void setDeliveriesMin(int deliveriesMin) {
        this.deliveriesMin = deliveriesMin;
    }

    /**
     * Set the maximum value of deliveries
     * @param deliveriesMax Maximum value of deliveries
     */
    public void setDeliveriesMax(int deliveriesMax) {
        this.deliveriesMax = deliveriesMax;
    }
}
