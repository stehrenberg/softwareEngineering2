/**Class representing the necessary data for each delivery.
 * @author Maximilian Renk
 */

package edu.hm.cs.softengii.db.sap;

import java.util.Date;

public class Delivery {

	private Date promisedDeliveryDate;
	
	private Date actualDeliveryDate;
	
    public Delivery(Date promisedDeliveryDate, Date actualDeliveryDate) {
    	this.promisedDeliveryDate = promisedDeliveryDate;
    	this.actualDeliveryDate = actualDeliveryDate;
    }
    
    public Date getPromisedDeliveryDate() {
    	return promisedDeliveryDate;
    }
    
    public Date getActualDeliveryDate() {
    	return actualDeliveryDate;
    }
}