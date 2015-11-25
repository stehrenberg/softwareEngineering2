/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/

package edu.hm.cs.softengii.db.sap;

/**
 * Represents a classification system for suppliers based on their total
 * number of deliveries.
 * @version 2015-11-18
 */
public enum SupplierClass {

    /** 20 or more deliveries */
    TOP(20, Integer.MAX_VALUE),
    /** 3 to 19 deliveries */
    NORMAL(3, 19),
    /** two deliveries or less */
    ONE_OFF(0, 2);

    private int deliveryCountLowerBorder;
    private int deliveryCountUpperBorder;

    private SupplierClass(int min, int max) {
        deliveryCountLowerBorder = min;
        deliveryCountUpperBorder = max;
    }

    public int getDeliveryCountLowerBorder() { return deliveryCountLowerBorder; }
    public int getDeliveryCountUpperBorder() { return deliveryCountUpperBorder; }
}
