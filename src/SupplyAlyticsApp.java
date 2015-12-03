import edu.hm.cs.softengii.cntrl.LoginCtrl;
import edu.hm.cs.softengii.cntrl.RegistrationCtrl;
import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.db.userAuth.DatabaseUserAuth;
import edu.hm.cs.softengii.utils.SettingsPropertiesHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to represent the main class of this program
 * @author Simon
 */
public class SupplyAlyticsApp extends Application {

	/**
	 * Current JavaFX stage
	 */
    private Stage stage;
    
    /**
     * Singleton instance of this class
     */
    private static SupplyAlyticsApp instance;

    /**
     * Create a new instance 
     */
    public SupplyAlyticsApp() {
        instance = this;
    }

    /**
     * Get singleton instance of this class
     * @return
     */
    public static SupplyAlyticsApp getInstance() {
        return instance;
    }

    /**
     * Main methods to start the pogram
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    	Database.createInstance();
    	
        primaryStage.setTitle(SettingsPropertiesHelper.getInstance().getAppTitle());
        stage = primaryStage;

        if(DatabaseUserAuth.getInstance().isEmpty()) {
            gotoRegistration();
        } else {
            gotoLogin();
        }

        primaryStage.show();
    }

    /**
     * Show registration page
     */
    private void gotoRegistration() {
        try {

            String fxmlPath = "edu/hm/cs/softengii/view/registration.fxml";
            FXMLLoader loader = new FXMLLoader (SupplyAlyticsApp.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((RegistrationCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);

        } catch (Exception ex) {
            Logger.getLogger(SupplyAlyticsApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Show login page
     */
    private void gotoLogin() {
        try {

            String fxmlPath = "edu/hm/cs/softengii/view/login.fxml";
            FXMLLoader loader = new FXMLLoader(SupplyAlyticsApp.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((LoginCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);

        } catch (Exception ex) {
            Logger.getLogger(SupplyAlyticsApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Replace the current scene content by another page
     * @param page New page to replace
     */
    private Parent replaceSceneContent(Parent page) throws Exception {

        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, SettingsPropertiesHelper.getInstance().getWindowWidth(), SettingsPropertiesHelper.getInstance().getWindowHeight());

            // TODO no CSS yet
            //scene.getStylesheets().add(App.class.getResource("demo.css").toExternalForm());

            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }
}
