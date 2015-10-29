package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.daos.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.soap.Node;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrateCtrl implements Initializable{

    private Stage stage;

    @FXML private AnchorPane registrateWindow;

    @FXML private Text welcomeText;

    @FXML private TextField forename;
    @FXML private TextField surname;

    @FXML private TextField userMail;

    @FXML private TextField userName;
    @FXML private PasswordField pswd;
    @FXML private PasswordField pswdConfirm;

    @FXML private Button registrateButton;

    @FXML
    void registrate(ActionEvent event) {
        UserDao userDao = new UserDao();
        userDao.createUser(userName.getText(), pswd.getText(), forename.getText(), surname.getText(), true);
        gotoMainMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //stage = (Stage) welcomeText.getScene().getWindow();

        welcomeText.setText("Welcome to the one minute SupplyAlytics registration! Just fill the Information below and you'll be \n" +
                "on your way to using the most powerful tool for supplier analytics.");
    }


    private void gotoMainMenu() {

        try {

            String fxmlPath = "../view/mainMenu.fxml";
            FXMLLoader loader = new FXMLLoader(RegistrateCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((MainMenuCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(RegistrateCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
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
