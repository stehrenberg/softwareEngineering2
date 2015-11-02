package edu.hm.cs.softengii.db.userAuth;

import edu.hm.cs.softengii.db.sap.Supplier;
import java.util.ArrayList;

/**Interface representing database access.
 * @author Maximilian Renk
 */
public interface IDatabaseUserAuth {

    /**Opens the connection to the database.*/
    void establishConnection();

    /**Close the connection to the database.*/
    void closeConnection();

    /**Finds all suppliers listed in the database and collects them in an ArrayList.
     * @return All suppliers in database.
     */
    ArrayList<String> getAllUsers();
    boolean isEmpty();
    UserEntity getUserFromLoginName(String loginName);
    boolean isLoginCorrect(String loginName, String password);
    UserEntity createNewUser(String loginName, String password, String forename, String surname, String email, boolean isAdmin);

}