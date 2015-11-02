package edu.hm.cs.softengii.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by cmon on 29.10.2015.
 */
public class LanguagePropertiesHelper {

    private Properties properties = new Properties();
    private String language = "en";
    private static LanguagePropertiesHelper instance = null;

    /**
     * Default-Konstruktor, der nicht au�erhalb dieser Klasse
     * aufgerufen werden kann
     */
    private LanguagePropertiesHelper() {
        try {
            properties.load(LanguagePropertiesHelper.class.getResourceAsStream("properties/language.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Statische Methode, liefert die einzige Instanz dieser
     * Klasse zur�ck
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
    public String getUserNameLabel() {
        return properties.getProperty(language + ".userNameLabel");
    }
    public String getPswdLabel() {
        return properties.getProperty(language + ".pswdLabel");
    }
    public String getPaswdHintText() {
        return properties.getProperty(language + ".paswdHintText");
    }
    public String getRegistrateButton() {
        return properties.getProperty(language + ".registrateButton");
    }


}
