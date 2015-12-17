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

            String fxmlPath = "/createNewUser.fxml";
            FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoManageAllUsers() {

        String fxmlPath = "/manageAllUsers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoCompareSuppliers() {

        String fxmlPath = "/compareSuppliers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoAverageSuppliers() {

        String fxmlPath = "/averageSuppliers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoUserSettings() {

        String fxmlPath = "/userSettings.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoAbout() {

        String fxmlPath = "/about.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoLogin() {

        Session.getInstance().close();

        String fxmlPath = "/login.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoPreferences() {

        String fxmlPath = "/preferences.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoScorePreferences() {

        String fxmlPath = "/scorePreferences.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoMainMenu() {

        String fxmlPath = "/compareSuppliers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoRegistration() {

        String fxmlPath = "/registration.fxml";
        FXMLLoader loader = new FXMLLoader (MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void quitApplication() {
        this.stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadPage(FXMLLoader loader, String fxmlPath) {

        try {
            Parent page = loader.load();
            replaceSceneContent(page);
        } catch (IOException ex) {
            String msg = ErrorMessagesPropertiesHelper.getInstance().getCouldNotLoadFileMsg(fxmlPath);
            Logger.getLogger(MenuHelper.class.getName()).log(Level.SEVERE, msg, ex);
        }

    }

    private Parent replaceSceneContent(Parent page) {

        Scene scene = stage.getScene();
        if (scene == null) {

            scene = new Scene(page, SettingsPropertiesHelper.getInstance().getWindowWidth(),
                    SettingsPropertiesHelper.getInstance().getWindowHeight());

            scene.getStylesheets().add(MenuHelper.class.getResource("/style.css").toExternalForm());
            stage.setScene(scene);

        } else {
            stage.getScene().setRoot(page);
        }

        return page;
    }
}
