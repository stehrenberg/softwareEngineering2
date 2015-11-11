/**Class representing the necessary data for each delivery.
 * @author Maximilian Renk
 */

package edu.hm.cs.softengii.db.sap;

import java.time.LocalDate;

public class Delivery {

	private LocalDate promisedDeliveryDate;

	private LocalDate actualDeliveryDate;

    public Delivery(LocalDate promisedDeliveryDate, LocalDate actualDeliveryDate) {
    	this.promisedDeliveryDate = promisedDeliveryDate;
    	this.actualDeliveryDate = actualDeliveryDate;
    }

    public LocalDate getPromisedDeliveryDate() {
    	return promisedDeliveryDate;
    }

    public LocalDate getActualDeliveryDate() {
    	return actualDeliveryDate;
    }
}