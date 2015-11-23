package edu.hm.cs.softengii.db.sap;

import java.util.List;

/**Interface representing database access.
 * @author Maximilian Renk
 */
public interface IDatabase {

    /**Opens the connection to the database.*/
    void establishConnection();

    /**Close the connection to the database.*/
    void closeConnection();

    /**Finds all suppliers listed in the database and collects them in as List.
     * @return All suppliers in database.
     */
    List<String> getSuppliersList();

    /**Collects all necessary data for each supplier and provides them as List.
     * @return necessary data for each supplier.
     */
    List<Supplier> getSupplierData();
}