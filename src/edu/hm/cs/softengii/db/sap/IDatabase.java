package edu.hm.cs.softengii.db.sap;

import java.util.ArrayList;

/**Interface representing database access.
 * @author Maximilian Renk
 */
public interface IDatabase {

    /**Opens the connection to the database.*/
    public void establishConnection();

    /**Close the connection to the database.*/
    public void closeConnection();

    /**Finds all suppliers listed in the database and collects them in an ArrayList.
     * @return All suppliers in database.
     */
    public ArrayList<String> getSuppliers();

    /**Collects all necessary data for each supplier and collects them in an ArrayList.
     * @return necessary data for each supplier.
     */
    public ArrayList<Supplier> getSupplierData();
}