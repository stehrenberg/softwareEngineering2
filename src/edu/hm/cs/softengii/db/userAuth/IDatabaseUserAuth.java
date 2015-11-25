/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Simon
Date: 20-10-2015
*/
package edu.hm.cs.softengii.db.userAuth;

import java.util.ArrayList;

/**
 * Interface to manage storing data in the database
 * @author Simon
 */
public interface IDatabaseUserAuth {

	/**
     * Establish a connection to the database
     */
    void establishConnection();

    /**
     * Close connection to the database
     */
    void closeConnection();

    /**
     * Get all users from the database
     */
    ArrayList<UserEntity> getAllUsers();
    
    /**
     * Check if there are users in the database
     */
    boolean isEmpty();
    
    /**
     * Get a specific user by its username
     */
    UserEntity getUserFromLoginName(String loginName);
    
    /**
     * Check if the login with this credentials is available
     */
    boolean isLoginCorrect(String loginName, String password);
    
    /**
     * Create a new admin user
     */
    UserEntity createNewAdminUser(String loginName, String password, String forename, String surname, String email);
    
    /**
     * Create a new normal user
     */
    UserEntity createNewUser(String loginName, String password, String forename, String surname, String email);
    
    /**
     * Delete the user with this login name
     * @param loginName Users login name
     */
    void deleteUserFromLoginName(String loginName);
    
    /**
     * Update the users properties
     */
    UserEntity updateUser(String loginName, String newLoginName, String password,
    		String forename, String surname, String email);

}