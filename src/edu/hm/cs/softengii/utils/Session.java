package edu.hm.cs.softengii.utils;

import edu.hm.cs.softengii.db.userAuth.UserEntity;

public class Session {

	/**
	 * Singleton instance of this class
	 */
    private static Session instance = null;
    
    /**
     * User that is authenticated
     */
    private UserEntity authenticatedUser;

    /**
     * Private Default-Constructor, can not be used outside of this class.
     */
    private Session() {}

    /**
     * Static helper method, that returns the single instance of this class.
     * @return instance
     */
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public UserEntity getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(UserEntity authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    /**
     * Close this session
     */
    public void close() {
        this.authenticatedUser = null;
    }
}
