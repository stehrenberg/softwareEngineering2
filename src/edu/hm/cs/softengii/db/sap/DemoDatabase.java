/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/

package edu.hm.cs.softengii.db.sap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo database containing mock data for testing purposes.
 */
public class DemoDatabase implements IDatabase {

    private static DemoDatabase instance = null;
    /** Contains mock supplier data. */
    List<Supplier> suppliers;

	/** Returns an instance of the mock db, if present - creates a new one otherwise. */
    public static DemoDatabase getInstance() {
        if (instance == null) {
        	instance = new DemoDatabase();
        }
        return instance;
    }

    @Override
    public void establishConnection() {
        suppliers = new ArrayList<>();
    }

    @Override
    public void closeConnection() {
    	suppliers.clear();
    }

    @Override
    public List<String> getSuppliersList() {

        List<String> supplierNames = new ArrayList<>();

        for (Supplier supplier: getSupplierData()) {
        	supplierNames.add(supplier.getName());
        }

        return supplierNames;
    }

    @Override
    public List<Supplier> getSupplierData() {

    	// Supplier 1
    	Supplier supplier1 = new Supplier("001", "MagicStuff GmbH");
    	supplier1.getDeliveries().add(new Delivery("001", LocalDate.of(2015, 10, 11), LocalDate.of(2015, 10, 12)));
    	supplier1.getDeliveries().add(new Delivery("002", LocalDate.of(2015, 3, 21), LocalDate.of(2015, 3, 21)));
    	supplier1.getDeliveries().add(new Delivery("003", LocalDate.of(2015, 1, 1), LocalDate.of(2015, 1, 1)));
    	supplier1.getDeliveries().add(new Delivery("004", LocalDate.of(2014, 11, 7), LocalDate.of(2014, 11, 18)));
    	suppliers.add(supplier1);

    	// Supplier 2
    	Supplier supplier2 = new Supplier("002", "Useless AG");
    	supplier2.getDeliveries().add(new Delivery("001", LocalDate.of(2015, 7, 1), LocalDate.of(2015, 7, 6)));
    	supplier2.getDeliveries().add(new Delivery("002", LocalDate.of(2014, 6, 15), LocalDate.of(2014, 6, 17)));
    	supplier2.getDeliveries().add(new Delivery("003", LocalDate.of(2013, 1, 1), LocalDate.of(2013, 1, 8)));
    	supplier2.getDeliveries().add(new Delivery("004", LocalDate.of(2012, 10, 14), LocalDate.of(2012, 10, 15)));
    	suppliers.add(supplier2);

    	// Supplier 3
    	Supplier supplier3 = new Supplier("003", "FUN AG");
    	supplier3.getDeliveries().add(new Delivery("001", LocalDate.of(2015, 10, 18), LocalDate.of(2015, 10, 18)));
    	supplier3.getDeliveries().add(new Delivery("002", LocalDate.of(2015, 3, 5), LocalDate.of(2015, 3, 5)));
    	supplier3.getDeliveries().add(new Delivery("003", LocalDate.of(2014, 8, 1), LocalDate.of(2014, 8, 1)));
    	suppliers.add(supplier3);

    	// Supplier 4
    	Supplier supplier4 = new Supplier("004", "Another AG");
    	supplier4.getDeliveries().add(new Delivery("001", LocalDate.of(2014, 9, 11), LocalDate.of(2014, 9, 13)));
    	supplier4.getDeliveries().add(new Delivery("002", LocalDate.of(2014, 2, 10), LocalDate.of(2014, 2, 10)));
    	supplier4.getDeliveries().add(new Delivery("003", LocalDate.of(2012, 2, 2), LocalDate.of(2012, 2, 1)));
    	suppliers.add(supplier4);

    	return suppliers;
    }
}
