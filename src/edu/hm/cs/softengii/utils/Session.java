package edu.hm.cs.softengii.utils;

import edu.hm.cs.softengii.db.userAuth.UserEntity;

/**
 * Created by cmon on 25.05.2014.
 */
public class Session {

    private static Session instance = null;

    private UserEntity authenticatedUser;

    /**
     * Default-Konstruktor, der nicht außerhalb dieser Klasse
     * aufgerufen werden kann
     */
    private Session() {}

    /**
     * Statische Methode, liefert die einzige Instanz dieser
     * Klasse zurück
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

    public void close() {
        this.authenticatedUser = null;
    }
}
