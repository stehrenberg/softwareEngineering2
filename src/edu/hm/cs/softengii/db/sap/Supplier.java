package edu.hm.cs.softengii.db.sap;

import java.util.ArrayList;
import java.util.List;

/**Class representing the necessary data for each supplier.
 * @author Maximilian Renk
 */
public class Supplier {

	/** Suppliers ID as provided by db. */
	private final String id;

    /**Name of the supplier.*/
    private final String name;

    /**Deliveries from this supplier.*/
    private final List<Delivery> deliveries = new ArrayList<>();

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

    public List<Delivery> getDeliveries() {
    	return deliveries;
    }
}
