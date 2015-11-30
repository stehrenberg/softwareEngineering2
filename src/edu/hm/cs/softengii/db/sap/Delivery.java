/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/

package edu.hm.cs.softengii.db.sap;

import java.time.LocalDate;

/**
 * Represents a delivery.
 */
public class Delivery {

    /** ID as found in the database (field EBELN). */
    private String deliveryID;
    /** Field SLFDT from db, represents the first promised delivery date. */
	private LocalDate promisedDeliveryDate;
    /** The actual goods receipt date.*/
	private LocalDate actualDeliveryDate;
    /** Difference between promised and actual delivery date. */
    private int delayInDays;

    /**
     * Ctor
     * @param deliveryID Unique delivery ID (EBELN from db)
     * @param promisedDeliveryDate Initially promised delivery date (by supplier) from db.
     * @param actualDeliveryDate Actual goods receipt date from db.
     */
    public Delivery(String deliveryID, LocalDate promisedDeliveryDate, LocalDate actualDeliveryDate) {
    	this.deliveryID = deliveryID;
        this.promisedDeliveryDate = promisedDeliveryDate;
    	this.actualDeliveryDate = actualDeliveryDate;
    }

    public String getDeliveryID() { return deliveryID; }

    public LocalDate getPromisedDeliveryDate() { return promisedDeliveryDate; }

    public LocalDate getActualDeliveryDate() { return actualDeliveryDate; }

    public int getDelay() { return delayInDays; }

    public void setDelay(int delayInDays) { this.delayInDays = delayInDays; }
}