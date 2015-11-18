package edu.hm.cs.softengii.db.sap;

/**
 * Represents a classification system for suppliers based on their total
 * number of deliveries.
 * @author Stephanie Ehrenberg
 * @version 2015-11-18
 */
public enum SupplierClass {

    /** 20 or more deliveries */
    TOP,
    /** 3 to 19 deliveries */
    NORMAL,
    /** two deliveries or less */
    ONE_OFF;
}
