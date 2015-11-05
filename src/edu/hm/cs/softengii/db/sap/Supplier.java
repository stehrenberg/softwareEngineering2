package edu.hm.cs.softengii.db.sap;

import java.util.ArrayList;

/**Class representing the necessary data for each supplier.
 * @author Maximilian Renk
 */
public class Supplier {

	/**
	 * Suppliers id
	 */
	private final String id;
	
    /**Name of the supplier.*/
    private final String name;

    /**Deliveries from this supplier.*/
    private final ArrayList<Delivery> deliveries = new ArrayList<>();

    public Supplier(final String id, final String name) {
    	this.id = id;
        this.name = name;
    }
    
    public String getId() {
    	return id;
    }
    
    public String getName() {
	    return name;
	}
    
    public ArrayList<Delivery> getDeliveries() {
    	return deliveries;
    }
}
