import edu.hm.cs.softengii.cntrl.LoginCtrl;
import edu.hm.cs.softengii.cntrl.RegistrateCtrl;
import edu.hm.cs.softengii.db.ApachenEntityManagerFactory;
import edu.hm.cs.softengii.db.daos.UserDao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SupplyAlyticsApp extends Application {


    private final int MAX_WIDTH = 1024;
    private final int MAX_HEIGHT = 768;

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

    @Override public void start(Stage primaryStage) {

        UserDao userDao = new UserDao();

        primaryStage.setTitle("NoKloo'boutIT - SupplyAlytics");
        stage = primaryStage;

        if(userDao.isEmpty()) {
            gotoRegistrate();
        } else {
            gotoLogin();
        }

        primaryStage.show();
    }


    private void gotoRegistrate() {
        try {

            String fxmlPath = "edu/hm/cs/softengii/view/registrate.fxml";
            FXMLLoader loader = new FXMLLoader (SupplyAlyticsApp.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((RegistrateCtrl)loader.getController()).setStage(stage);

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
            scene = new Scene(page, 1024, 768);

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
