package edu.hm.cs.softengii.utils;

import java.io.IOException;
import java.util.Properties;

public class SettingsPropertiesHelper {

    private Properties properties = new Properties();
    private static SettingsPropertiesHelper instance = null;

    /**
     * Default-Constructor, can not be used outside of this class.
     */
    private SettingsPropertiesHelper() {
        try {
            properties.load(SettingsPropertiesHelper.class.getResourceAsStream("properties/settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static helper method, that returns the single instance of this class.
     * @return instance
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

    public String getDataStorageDbUrl() {
        return properties.getProperty("dataStorage.db.url");
    }

    public String getDataStorageDbUser() {
        return properties.getProperty("dataStorage.db.user");
    }

    public String getDataStorageDbPswd() {
        return properties.getProperty("dataStorage.db.pswd");
    }

}
