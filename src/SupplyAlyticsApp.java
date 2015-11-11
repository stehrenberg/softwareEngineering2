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

import java.util.logging.Level;
import java.util.logging.Logger;

public class SupplyAlyticsApp extends Application {

    private Stage stage;
    private static SupplyAlyticsApp instance;

    public SupplyAlyticsApp() {
        instance = this;
    }

    public static SupplyAlyticsApp getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    	//Database.createInstance();

        primaryStage.setTitle(SettingsPropertiesHelper.getInstance().getAppTitle());
        stage = primaryStage;

        if(DatabaseUserAuth.getInstance().isEmpty()) {
            gotoRegistration();
        } else {
            gotoLogin();
        }

        primaryStage.show();
    }


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
