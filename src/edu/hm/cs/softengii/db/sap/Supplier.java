/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/

package edu.hm.cs.softengii.db.sap;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the necessary data for each supplier.
 */
public class Supplier {

    /** Suppliers ID as provided by db. */
    private final String id;

    /** Name of the supplier.*/
    private final String name;

    /** Classification based on the supplier's total amount of deliveries. */
    private SupplierClass supplierClass;

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

    public SupplierClass getSupplierClass() { return supplierClass; }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setSupplierClass(SupplierClass suppClass) { supplierClass = suppClass; }
}
