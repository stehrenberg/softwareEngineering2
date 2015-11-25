/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
Author: Simon
Date: 20-10-2015
*/
package edu.hm.cs.softengii.db.userAuth;

import edu.hm.cs.softengii.utils.PasswordGen;
import edu.hm.cs.softengii.utils.SettingsPropertiesHelper;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Wrapper class to manage storing data in the database
 * @author Simon
 */
public class DatabaseUserAuth implements IDatabaseUserAuth {

	// Fields ---------------------------------------------------------------------------

	/** Singleton instance of this class */
    private static DatabaseUserAuth instance = null;

    /** Database URL */
    private final static String DB_URL = SettingsPropertiesHelper.getInstance().getUserAuthDbUrl();

    /** Database username */
    private final static String USER = SettingsPropertiesHelper.getInstance().getUserAuthDbUser();

    /** Database password */
    private final static String PASSWORD = SettingsPropertiesHelper.getInstance().getUserAuthDbPswd();

    /** Connection to the database */
    private Connection connection;

    // Ctor -----------------------------------------------------------------------------

    /**
     * Create a new instance
     */
    private DatabaseUserAuth() {
        this.establishConnection();
    }

    // Static methods -------------------------------------------------------------------

    /**
     * Get the singleton instance of this class
     * @return Instance of this class
     */
    public static DatabaseUserAuth getInstance() {
        if (instance == null) {
            instance = new DatabaseUserAuth();
        }
        return instance;
    }

    // Public methods -------------------------------------------------------------------

    /**
     * Establish a connection to the database
     */
    @Override
    public void establishConnection() {

        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close connection to the database
     */
    @Override
    public void closeConnection() {

        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all users from the database
     */
    @Override
    public ArrayList<UserEntity> getAllUsers() {

        this.establishConnection();

        ArrayList<String> userLogins = new ArrayList<>();
        ArrayList<UserEntity> users = new ArrayList<>();

        try {
            Statement statement = this.connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM Users");

            while(set.next()) {
                userLogins.add(set.getString("loginName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.closeConnection();

        for (String login : userLogins) {
            users.add(getUserFromLoginName(login));
        }

        return users;
    }

    /**
     * Check if there are users in the database
     */
    @Override
    public boolean isEmpty() {
        return this.getAllUsers().isEmpty();
    }

    /**
     * Get a specific user by its username
     */
    @Override
    public UserEntity getUserFromLoginName(String loginName) {

        this.establishConnection();

        UserEntity tmpUser = null;

        try {
            String query = String.format("SELECT * FROM Users WHERE loginName LIKE '%s'", loginName);
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {
                if (loginName.equals(set.getString("loginName"))) {
                    String forenameTmp = set.getString("forename");
                    String surnameTmp = set.getString("surname");
                    String loginNameTmp = set.getString("loginName");
                    String emailTmp = set.getString("email");
                    boolean isAdminTmp = set.getBoolean("isAdmin");
                    tmpUser = new UserEntity(isAdminTmp, forenameTmp, surnameTmp, loginNameTmp, emailTmp);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.closeConnection();

        return tmpUser;
    }

    /**
     * Check if the login with this credentials is available
     */
    @Override
    public boolean isLoginCorrect(String loginName, String password) {

        this.establishConnection();

        ArrayList<String> users = new ArrayList<>();

        String generatedPass = PasswordGen.generatePassword(password);


        try {
            String query = String.format("SELECT * FROM Users WHERE loginName = '%s' AND pswd = '%s'",
                    loginName, generatedPass);

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {
                users.add(set.getString("loginName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.closeConnection();

        return (users.size() == 1);
    }

    /**
     * Create a new admin user
     */
    @Override
    public UserEntity createNewAdminUser(String loginName, String password,
    		String forename, String surname, String email) {

        return this.createUser(loginName, password, forename, surname, email, true);
    }

    /**
     * Create a new normal user
     */
    @Override
    public UserEntity createNewUser(String loginName, String password,
    		String forename, String surname, String email) {

        return createUser(loginName, password, forename, surname, email, false);
    }

    /**
     * Delete the user with this login name
     * @param loginName Users login name
     */
    @Override
    public void deleteUserFromLoginName(String loginName) {
        establishConnection();
        try {
            String query = String.format("DELETE FROM Users WHERE loginName = '%s'",loginName);
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    /**
     * Update the users properties
     */
    @Override
    public UserEntity updateUser(String loginName, String newLoginName, String password,
    		String forename, String surname, String email) {

        this.establishConnection();

        String generatedPswd = "";
        UserEntity newUser = null;

        if (!password.equals("")){
            generatedPswd = PasswordGen.generatePassword(password);
        }

        try {

            String query = "";
            if (!password.equals("")) {
                query = String.format("UPDATE Users " +
                                "SET forename='%s', surname='%s', loginName='%s', pswd='%s', email='%s'" +
                                "WHERE loginName='%s'",
                        forename, surname, newLoginName, generatedPswd, email, loginName);
            } else {
                query = String.format("UPDATE Users " +
                                "SET forename='%s', surname='%s', loginName='%s', email='%s'" +
                                "WHERE loginName='%s'",
                        forename, surname, newLoginName, email, loginName);
            }
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.closeConnection();

        return this.getUserFromLoginName(newLoginName);
    }

    // Private methods ------------------------------------------------------------------

    /**
     * Create a new user in the database
     * @param loginName USers login name
     * @param password Users password
     * @param forename Users forename
     * @param surname Users surname
     * @param email Users mail address
     * @param isAdmin User is admin
     * @return User entity object
     */
    private UserEntity createUser(String loginName, String password,
    		String forename, String surname, String email, boolean isAdmin) {

        this.establishConnection();

        UserEntity newUser = null;
        String generatedPswd = PasswordGen.generatePassword(password);

        try {
            int admin = 0;
            if (isAdmin){
                admin = 1;
            }

            String query = String.format("INSERT INTO Users (forename, surname, loginName, pswd, email, isAdmin)" +
                            "VALUES('%s','%s', '%s', '%s', '%s', '%d')",
                    forename, surname, loginName, generatedPswd, email, admin);

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            newUser = new UserEntity(isAdmin, forename, surname, loginName, email);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.closeConnection();

        return newUser;
    }
}