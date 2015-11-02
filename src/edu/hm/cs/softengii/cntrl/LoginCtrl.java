package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.userAuth.DatabaseUserAuth;
import edu.hm.cs.softengii.db.userAuth.UserEntity;
import edu.hm.cs.softengii.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCtrl implements Initializable{

    private Stage stage;

    @FXML private TextField userName;
    @FXML private PasswordField pswd;
    @FXML private Button loginButton;

    @FXML
    void loginToMainMenu(ActionEvent event) {

        if(DatabaseUserAuth.getInstance().isLoginCorrect(userName.getText(), pswd.getText())) {

            Session.getInstance().setAuthenticatedUser(DatabaseUserAuth.getInstance().getUserFromLoginName(userName.getText()));

            gotoMainMenu();

        } else {
            System.out.println("ERROR: Wrong login data!");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    private void gotoMainMenu() {

        try {

            String fxmlPath = "../view/compareSuppliers.fxml";
            FXMLLoader loader = new FXMLLoader(LoginCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((CompareSuppliersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(LoginCtrl.class.getName()).log(Level.SEVERE, null, ex);
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
