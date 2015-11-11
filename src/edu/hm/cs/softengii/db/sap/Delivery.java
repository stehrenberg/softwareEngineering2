/**Class representing the necessary data for each delivery.
 * @author Maximilian Renk
 */

package edu.hm.cs.softengii.db.sap;

import java.time.LocalDate;

public class Delivery {

    private String deliveryID;
	private LocalDate promisedDeliveryDate;

	private LocalDate actualDeliveryDate;

    private int delayInDays;

    public Delivery(String deliveryID, LocalDate promisedDeliveryDate, LocalDate actualDeliveryDate) {
    	this.deliveryID = deliveryID;
        this.promisedDeliveryDate = promisedDeliveryDate;
    	this.actualDeliveryDate = actualDeliveryDate;
    }
    public String getDeliveryID() { return deliveryID; }

    public LocalDate getPromisedDeliveryDate() {
    	return promisedDeliveryDate;
    }

    public LocalDate getActualDeliveryDate() {
    	return actualDeliveryDate;
    }

    public int getDelay() { return delayInDays; }

    public void setDelay(int delayInDays) {
        this.delayInDays = delayInDays;
    }
}