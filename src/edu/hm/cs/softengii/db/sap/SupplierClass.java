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

    /** TOP suppliers have 20 and more deliveries. */
    TOP(20, Integer.MAX_VALUE),
    /** NORMAL means 3 to 19 deliveries. */
    NORMAL(3, 19),
    /** ONE_OFF equals rather insignificant suppliers with 2 or less deliveries. */
    ONE_OFF(0, 2);

    /** Specifies the minimum number of deliveries for class. */
    private int deliveryCountLowerBorder;
    /** Specifies the maximum number of deliveries for class. */
    private int deliveryCountUpperBorder;

    /**
     * Ctor for setting min and max delivery count.
     * @param min Minimum amount of deliveries for the specific supplier class.
     * @param max Maximum amount of deliveries for the specific supplier class.
     */
    private SupplierClass(int min, int max) {
        deliveryCountLowerBorder = min;
        deliveryCountUpperBorder = max;
    }

    public int getDeliveryCountLowerBorder() { return deliveryCountLowerBorder; }
    public int getDeliveryCountUpperBorder() { return deliveryCountUpperBorder; }
}
