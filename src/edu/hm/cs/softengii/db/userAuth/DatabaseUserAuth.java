package edu.hm.cs.softengii.db.userAuth;

import edu.hm.cs.softengii.utils.PasswordGen;
import edu.hm.cs.softengii.utils.SettingsPropertiesHelper;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseUserAuth implements IDatabaseUserAuth {

    private static DatabaseUserAuth instance = null;

    //Database URL
    private final static String DB_URL = SettingsPropertiesHelper.getInstance().getUserAuthDbUrl();

    //Database credentials
    private final static String USER = SettingsPropertiesHelper.getInstance().getUserAuthDbUser();
    private final static String PASSWORD = SettingsPropertiesHelper.getInstance().getUserAuthDbPswd();

    /**Connection to the database.*/
    private Connection connection;

    /**Empty constructor. Not needed here.*/
    private DatabaseUserAuth() {
        establishConnection();
    }


    public static DatabaseUserAuth getInstance() {
        if (instance == null) {
            instance = new DatabaseUserAuth();
        }
        return instance;
    }


    @Override
    public void establishConnection() {
        try {
            if(connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getAllUsers() {

        establishConnection();
        ArrayList<String> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM Users");

            while(set.next()) {
                users.add(set.getString("loginName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return users;
    }

    @Override
    public boolean isEmpty() {
        return getAllUsers().isEmpty();
    }

    @Override
    public UserEntity getUserFromLoginName(String loginName) {

        establishConnection();
        UserEntity tmpUser = null;

        try {
            String query = String.format("SELECT * FROM Users WHERE loginName LIKE '%s'", loginName);
            System.out.println("query: " + query);

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {
                if(loginName.equals(set.getString("loginName"))) {
                    String forenameTmp = set.getString("forename");
                    String surnameTmp = set.getString("surname");
                    String loginNameTmp = set.getString("loginName");
                    String emailTmp = set.getString("email");
                    boolean isAdminTmp = set.getBoolean("isAdmin");
                    System.out.println("createdUser");
                    tmpUser = new UserEntity(isAdminTmp, forenameTmp, surnameTmp, loginNameTmp, emailTmp);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return tmpUser;
    }


    @Override
    public boolean isLoginCorrect(String loginName, String password) {

        establishConnection();
        ArrayList<String> users = new ArrayList<>();

        String generatedPass = "";
        try {

            generatedPass = PasswordGen.generatePassword(password);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {

            String query = String.format("SELECT * FROM Users WHERE loginName = '%s' AND pswd = '%s'",
                    loginName, generatedPass);

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {
                users.add(set.getString("loginName"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return (users.size() == 1);
    }

    @Override
    public UserEntity createNewUser(String loginName, String password, String forename, String surname, String email, boolean isAdmin) {

        establishConnection();

        String generatedPswd = "";
        UserEntity newUser = null;
        try {

            generatedPswd = PasswordGen.generatePassword(password);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {

            String query = String.format("INSERT INTO Users (forename, surname, loginName, pswd, email, isAdmin)" +
                            "VALUES('%s','%s', '%s', '%s', '%s', '%b')",
                    forename, surname, loginName, generatedPswd, email, isAdmin);

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            newUser = new UserEntity(isAdmin, forename, surname, loginName, email);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return newUser;

    }
}