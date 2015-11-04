/**Class representing the necessary data for each supplier.
 * @author Maximilian Renk
 */

package edu.hm.cs.softengii.db.sap;

import java.util.ArrayList;

public class Supplier {
    
	/**
	 * Supplier id
	 */
	private String id;
	
    /**Name of the supplier.*/
    private final String supplier;
    
    /**Deliveries from this supplier.*/
    private ArrayList<Delivery> deliveries = new ArrayList<>();
    
    public Supplier(final String id, final String supplier, ArrayList<Delivery> deliveries) {
    	this.id = id;
        this.supplier = supplier;
        this.deliveries = deliveries;
    }
    
    /**
     * Get the id of this supplier
     * @return Suppliers id
     */
    public String getId() {
    	return id;
    }
    
    /**
     * Get the suppliers name
     * @return Suppliers name
     */
    public String getName() {
    	return supplier;
    }
    
    /**
     * Get all deliveries of this supplier
     * @return List with all deliveries
     */
    public ArrayList<Delivery> getDeliveries() {
    	return deliveries;
    }
}