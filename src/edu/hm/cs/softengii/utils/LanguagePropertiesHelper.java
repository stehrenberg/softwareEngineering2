package edu.hm.cs.softengii.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Class to get values from property entries.
 */
public class LanguagePropertiesHelper {

    private String language = "en"; // default language
    private Properties properties = new Properties();
    private static LanguagePropertiesHelper instance = null;

    /**
     * Private Default-Constructor, can not be used outside of this class.
     */
    private LanguagePropertiesHelper() {
        try {
            properties.load(LanguagePropertiesHelper.class.getResourceAsStream("properties/language.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static helper method, that returns the single instance of this class.
     */
    public static LanguagePropertiesHelper getInstance() {
        if (instance == null) {
            instance = new LanguagePropertiesHelper();
        }
        return instance;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getWelcomeHeadline() {
        return properties.getProperty(language + ".welcomeHeadline");
    }

    public String getWelcomeText() {
        return properties.getProperty(language + ".welcomeText");
    }

    public String getWelcomeTextLogin() {
        return properties.getProperty(language + ".welcomeTextLogin");
    }

    public String getInfoHeadline() {
        return properties.getProperty(language + ".infoHeadline");
    }

    public String getInfoText() {
        return properties.getProperty(language + ".infoText");
    }

    public String getForenameLabel() {
        return properties.getProperty(language + ".forenameLabel");
    }

    public String getSurnameLabel() {
        return properties.getProperty(language + ".surnameLabel");
    }

    public String getUserMailLabel() {
        return properties.getProperty(language + ".userMailLabel");
    }

    public String getPswdLabel() { return properties.getProperty(language + ".pswdLabel"); }

    public String getUserNameLabel() {
        return properties.getProperty(language + ".userNameLabel");
    }

    public String getPaswdHintText() {
        return properties.getProperty(language + ".paswdHintText");
    }

    public String getRegisterButton() {
        return properties.getProperty(language + ".registerButton");
    }

    public String getForenameError() {
        return properties.getProperty(language + ".forenameError");
    }

    public String getSurnameError() {
        return properties.getProperty(language + ".surnameError");
    }

    public String getUserMailEmptyError() {
        return properties.getProperty(language + ".userMailEmptyError");
    }

    public String getUserMailError() {
        return properties.getProperty(language + ".userMailError");
    }

    public String getUserNameExistsError() {
        return properties.getProperty(language + ".userNameExistsError");
    }

    public String getUserNameError() {
        return properties.getProperty(language + ".userNameError");
    }

    public String getPswdError() {
        return properties.getProperty(language + ".pswdError");
    }

    public String getPswdConfirmError() {
        return properties.getProperty(language + ".pswdConfirmError");
    }

    public String getPswdNoMatchError() {
        return properties.getProperty(language + ".pswdNoMatchError");
    }

    public String getLoginError() {
        return properties.getProperty(language + ".loginError");
    }

    public String getAboutDevelopers() {
        return properties.getProperty("about_developers");
    }

    public String getGitReference() {
        return properties.getProperty("git_ref");
    }
}
