/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/

package edu.hm.cs.softengii.db.sap;

/**
 * Represents a classification system for suppliers based on their total
 * number of deliveries.
 */
public enum SupplierClass {

    TOP(20, Integer.MAX_VALUE),
    NORMAL(3, 19),
    ONE_OFF(0, 2);

    /** Specifies the minimum number of deliveries for class. */
    private int deliveryCountLowerBorder;
    /** Specifies the maximum number of deliveries for class. */
    private int deliveryCountUpperBorder;

    private SupplierClass(int min, int max) {
        deliveryCountLowerBorder = min;
        deliveryCountUpperBorder = max;
    }

    public int getDeliveryCountLowerBorder() { return deliveryCountLowerBorder; }
    public int getDeliveryCountUpperBorder() { return deliveryCountUpperBorder; }
}
