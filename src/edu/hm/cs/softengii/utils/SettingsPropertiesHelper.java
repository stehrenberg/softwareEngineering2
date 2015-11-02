package edu.hm.cs.softengii.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by cmon on 29.10.2015.
 */
public class SettingsPropertiesHelper {

    private Properties properties = new Properties();
    private static SettingsPropertiesHelper instance = null;

    /**
     * Default-Konstruktor, der nicht außerhalb dieser Klasse
     * aufgerufen werden kann
     */
    private SettingsPropertiesHelper() {
        try {
            properties.load(SettingsPropertiesHelper.class.getResourceAsStream("properties/settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Statische Methode, liefert die einzige Instanz dieser
     * Klasse zurück
     */
    public static SettingsPropertiesHelper getInstance() {
        if (instance == null) {
            instance = new SettingsPropertiesHelper();
        }
        return instance;
    }

    public int getWindowHeight() {
        return Integer.parseInt(properties.getProperty("windowHeight"));
    }
    public int getWindowWidth() {
        return Integer.parseInt(properties.getProperty("windowWidth"));
    }
    public String getAppTitle() {
        return properties.getProperty("appTitle");
    }

    public String getSapDbUrl() {
        return properties.getProperty("sap.db.url");
    }
    public String getSapDbUser() {
        return properties.getProperty("sap.db.user");
    }
    public String getSapDbPswd() {
        return properties.getProperty("sap.db.pswd");
    }

    public String getUserAuthDbUrl() {
        return properties.getProperty("userAuth.db.url");
    }
    public String getUserAuthDbUser() {
        return properties.getProperty("userAuth.db.user");
    }
    public String getUserAuthDbPswd() {
        return properties.getProperty("userAuth.db.pswd");
    }



}
