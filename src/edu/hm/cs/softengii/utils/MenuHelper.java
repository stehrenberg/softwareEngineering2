package edu.hm.cs.softengii.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuHelper {

    private Stage stage;
    private static MenuHelper instance = null;

    /**
     * Private Default-Constructor, can not be used outside of this class.
     */
    protected MenuHelper() {}

    /**
     * Static helper method, that returns the single instance of this class.
     * @return instance
     */
    public static MenuHelper getInstance() {
        if (instance == null) {
            instance = new MenuHelper();
        }
        return instance;
    }

    public void gotoCreateNewUser(){

            String fxmlPath = "../view/createNewUser.fxml";
            FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        try {

            Parent page = loader.load();
            replaceSceneContent(page);

        } catch (IOException ex) {
        	String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotLoadFileMsg(fxmlPath);
            Logger.getLogger(MenuHelper.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }

    public void gotoManageAllUsers() {

        String fxmlPath = "../view/manageAllUsers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        try {

            Parent page = loader.load();
            replaceSceneContent(page);

        } catch (IOException ex) {
            String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotLoadFileMsg(fxmlPath);
            Logger.getLogger(MenuHelper.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }

    public void gotoCompareSuppliers() {

        String fxmlPath = "../view/compareSuppliers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        try {

            Parent page = loader.load();
            replaceSceneContent(page);

        } catch (IOException ex) {
        	String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotLoadFileMsg(fxmlPath);
            Logger.getLogger(MenuHelper.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }

    public void gotoAverageSuppliers() {

        String fxmlPath = "../view/averageSuppliers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        try {

            Parent page = loader.load();
            replaceSceneContent(page);

        } catch (IOException ex) {
        	String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotLoadFileMsg(fxmlPath);
            Logger.getLogger(MenuHelper.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }

    public void gotoUserSettings() {

        String fxmlPath = "../view/userSettings.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        try {

            Parent page = loader.load();
            replaceSceneContent(page);

        } catch (IOException ex) {
        	String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotLoadFileMsg(fxmlPath);
            Logger.getLogger(MenuHelper.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }

    public void gotoAbout() {

        String fxmlPath = "../view/about.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        try {

            Parent page = loader.load();
            replaceSceneContent(page);

        } catch (IOException ex) {
        	String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotLoadFileMsg(fxmlPath);
            Logger.getLogger(MenuHelper.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }

    public void gotoLogin() {

        Session.getInstance().close();

        String fxmlPath = "../view/login.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        try {

            Parent page = loader.load();
            replaceSceneContent(page);

        } catch (IOException ex) {
        	String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotLoadFileMsg(fxmlPath);
            Logger.getLogger(MenuHelper.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }

    public void gotoPreferences() {

        String fxmlPath = "../view/preferences.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        try {

            Parent page = loader.load();
            replaceSceneContent(page);

        } catch (IOException ex) {
        	String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotLoadFileMsg(fxmlPath);
            Logger.getLogger(MenuHelper.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }

    public void gotoMainMenu() {

        String fxmlPath = "../view/compareSuppliers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        try {

            Parent page = loader.load();
            replaceSceneContent(page);

        } catch (IOException ex) {
        	String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotLoadFileMsg(fxmlPath);
            Logger.getLogger(MenuHelper.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }

    public void gotoRegistration() {

        String fxmlPath = "../view/registration.fxml";
        FXMLLoader loader = new FXMLLoader (MenuHelper.class.getResource(fxmlPath));

        try {

            Parent page = loader.load();
            replaceSceneContent(page);

        } catch (IOException ex) {
        	String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotLoadFileMsg(fxmlPath);
            Logger.getLogger(MenuHelper.class.getName()).log(Level.SEVERE, msg, ex);
        }
    }

    public void quitApplication() {
        this.stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Parent replaceSceneContent(Parent page) {

        Scene scene = stage.getScene();
        if (scene == null) {

            scene = new Scene(page, SettingsPropertiesHelper.getInstance().getWindowWidth(),
                    SettingsPropertiesHelper.getInstance().getWindowHeight());

            stage.setScene(scene);

        } else {
            stage.getScene().setRoot(page);
        }

        stage.sizeToScene();
        return page;
    }
}
