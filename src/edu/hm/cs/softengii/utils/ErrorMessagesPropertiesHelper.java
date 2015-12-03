package edu.hm.cs.softengii.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Class to get values from property entries.
 */
public class ErrorMessagesPropertiesHelper {

    private String language = "en"; // default language
    private Properties properties = new Properties();
    private static ErrorMessagesPropertiesHelper instance = null;

    /**
     * Private Default-Constructor, can not be used outside of this class.
     */
    private ErrorMessagesPropertiesHelper() {
        try {
            properties.load(ErrorMessagesPropertiesHelper.class.getResourceAsStream("properties/errorMessages.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static helper method, that returns the single instance of this class.
     */
    public static ErrorMessagesPropertiesHelper getInstance() {
        if (instance == null) {
            instance = new ErrorMessagesPropertiesHelper();
        }
        return instance;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCouldNotLoadFileMsg(String fxmlPath) {
        return properties.getProperty(language + ".msg.couldNotLoadFile") + fxmlPath;
    }
    
    public String getCouldNotGeneratePassword() {
    	return properties.getProperty(language + ".msg.couldNotGeneratePassword");
    }
}
