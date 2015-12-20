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

            String fxmlPath = "/edu/hm/cs/softengii/view/createNewUser.fxml";
            FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoManageAllUsers() {

        String fxmlPath = "/edu/hm/cs/softengii/view/manageAllUsers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoCompareSuppliers() {

        String fxmlPath = "/edu/hm/cs/softengii/view/compareSuppliers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoAverageSuppliers() {

        String fxmlPath = "/edu/hm/cs/softengii/view/averageSuppliers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoUserSettings() {

        String fxmlPath = "/edu/hm/cs/softengii/view/userSettings.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoAbout() {

        String fxmlPath = "/edu/hm/cs/softengii/view/about.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoLogin() {

        Session.getInstance().close();

        String fxmlPath = "/edu/hm/cs/softengii/view/login.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoPreferences() {

        String fxmlPath = "/edu/hm/cs/softengii/view/preferences.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoScorePreferences() {

        String fxmlPath = "/edu/hm/cs/softengii/view/scorePreferences.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoDeliveryPreferences() {

        String fxmlPath = "/edu/hm/cs/softengii/view/deliveryPreferences.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoMainMenu() {

        String fxmlPath = "/edu/hm/cs/softengii/view/compareSuppliers.fxml";
        FXMLLoader loader = new FXMLLoader(MenuHelper.class.getResource(fxmlPath));

        loadPage(loader, fxmlPath);
    }

    public void gotoRegistration() {

        String fxmlPath = "/edu/hm/cs/softengii/view/registration.fxml";
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

            scene.getStylesheets().add(MenuHelper.class.getResource("/edu/hm/cs/softengii/view/css/style.css").toExternalForm());
            stage.setScene(scene);

        } else {
            stage.getScene().setRoot(page);
        }

        return page;
    }
}
