/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/

package edu.hm.cs.softengii.db.sap;

import java.util.List;

/**
 * Interface representing database access methods.
 */
public interface IDatabase {

    /** Open the connection to the database.
     * @return */
    boolean establishConnection();

    /** Close the connection to the database.*/
    void closeConnection();

    /**
     * Finds all suppliers listed in the database and collects them in a List.
     * @return All suppliers in database.
     */
    List<String> getSuppliersList();

    /**
     * Collects all necessary data for each supplier and provides them as List.
     * @return necessary data for each supplier.
     */
    List<Supplier> getSupplierData();
}
