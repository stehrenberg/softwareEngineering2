/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Simon
Date: 20-10-2015
*/

package edu.hm.cs.softengii.db.userAuth;

/**
 * Class to represent a user database entity
 * @author Simon
 */
public class UserEntity {

	// Fields ---------------------------------------------------------------------------
	
	/** Users forename */
    private String forename;
    
    /** Users surname */
    private String surname;
    
    /** Users login name */
    private String loginName;
    
    /** Users password */
    private String password;
    
    /** Users mail address */
    private String email;
    
    /** The user is admin */
    private boolean isAdmin;

    // Ctor -----------------------------------------------------------------------------

    /**
     * Create a new empty instance
     */
    public UserEntity() {
    }

    /**
     * Create a new instance
     * @param isAdmin The user is admin
     * @param forename Users forename
     * @param surname Users surname
     * @param loginName Users login name
     * @param email Users mail address
     */
    public UserEntity(boolean isAdmin, String forename, String surname, String loginName, String email) {
    	
        this.isAdmin = isAdmin;
        this.forename = forename;
        this.surname = surname;
        this.loginName = loginName;
        this.email = email;
    }

    // Getter ---------------------------------------------------------------------------
    
    /**
     * Get the users forename
     * @return Users forename
     */
    public String getForename() {

        if (this.forename == null) {
            return "";
        }
        
        return this.forename;
    }

    /**
     * Get the users surname
     * @return USers surname
     */
    public String getSurname() {

        if (this.surname == null) {
            return "";
        }
        
        return this.surname;
    }

    /**
     * Get the users login name
     * @return Users login name
     */
    public String getLoginName() {
        return this.loginName;
    }

    /**
     * Get the users password
     * @return Users password
     */
    public String getPassword() {
        return this.password;
    }

    /** 
     * Get the users mail address
     * @return Users mail address
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Check if the users is admin
     * @return User is admin
     */
    public boolean isAdmin() {
        return this.isAdmin;
    }
    
    // Setter ---------------------------------------------------------------------------

    /**
     * Set the users forename
     * @param forename Users forename
     */
    public void setForename(String forename) {
        this.forename = forename;
    }

    /**
     * Set the users surname
     * @param surname Users surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Set the users login name
     * @param loginName Users login name
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /** 
     * Set the users password
     * @param password Users password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the users mail address
     * @param email Users mail address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set if the user is admin
     * @param isAdmin User is admin
     */
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
