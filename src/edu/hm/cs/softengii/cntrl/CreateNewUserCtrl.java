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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateNewUserCtrl implements Initializable{

    private Stage stage;

    @FXML private AnchorPane rootPane;

    @FXML private Text forenameLabel;
    @FXML private TextField forename;
    @FXML private Text surnameLabel;
    @FXML private TextField surname;
    @FXML private Text userMailLabel;
    @FXML private TextField userMail;
    @FXML private Text userNameLabel;
    @FXML private TextField userName;
    @FXML private Text pswdLabel;
    @FXML private Text paswdHintText;
    @FXML private PasswordField pswd;
    @FXML private PasswordField pswdConfirm;
    @FXML private Text message;

    @FXML
    void createUser(ActionEvent event) {


        UserEntity newUser = DatabaseUserAuth.getInstance().createNewUser(userName.getText(), pswd.getText(), forename.getText(), surname.getText(), userMail.getText(), true);

        message.setText(String.format("Created new user: %s %s (%s)", newUser.getForename(), newUser.getSurname(), newUser.getLoginName()));
    }

    @FXML
    void gotoCreateNewUser(ActionEvent event) {

        //Do nothing, we are already here
    }

    @FXML
    void gotoManageAllUsers(ActionEvent event) {

        try {

            String fxmlPath = "../view/manageAllUsers.fxml";
            FXMLLoader loader = new FXMLLoader(CreateNewUserCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((ManageAllUsersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(CreateNewUserCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoCompareSuppliers(ActionEvent event) {

        try {

            String fxmlPath = "../view/compareSuppliers.fxml";
            FXMLLoader loader = new FXMLLoader(CreateNewUserCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((CompareSuppliersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(CreateNewUserCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoAverageSuppliers(ActionEvent event) {

        try {

            String fxmlPath = "../view/averageSuppliers.fxml";
            FXMLLoader loader = new FXMLLoader(CreateNewUserCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((AverageSuppliersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(CreateNewUserCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void gotoUserSettings(ActionEvent event) {

        try {

            String fxmlPath = "../view/userSettings.fxml";
            FXMLLoader loader = new FXMLLoader(CreateNewUserCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((UserSettingsCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(CreateNewUserCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoAbout(ActionEvent event) {

        try {

            String fxmlPath = "../view/about.fxml";
            FXMLLoader loader = new FXMLLoader(CreateNewUserCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((AboutCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(CreateNewUserCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoLogin(ActionEvent event) {

        Session.getInstance().close();

        try {

            String fxmlPath = "../view/login.fxml";
            FXMLLoader loader = new FXMLLoader(UserSettingsCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((LoginCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(UserSettingsCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void quitApplication(ActionEvent event) {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Parent replaceSceneContent(Parent page) throws Exception {

        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, 640, 480);

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
