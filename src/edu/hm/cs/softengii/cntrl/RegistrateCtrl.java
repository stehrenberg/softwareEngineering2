package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.daos.UserDao;
import edu.hm.cs.softengii.db.entities.UserEntity;
import edu.hm.cs.softengii.utils.LanguagePropertiesHelper;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrateCtrl implements Initializable{

    private Stage stage;

    @FXML private Text welcomeHeadline;
    @FXML private Text welcomeText;
    @FXML private Text infoHeadline;
    @FXML private Text infoText;

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

    @FXML private Button registrateButton;
    @FXML private Button germanButton;
    @FXML private Button englishButton;

    @FXML
    void registrate(ActionEvent event) {

        UserDao userDao = new UserDao();

        UserEntity newUser = userDao.createUser(userName.getText(), pswd.getText(), forename.getText(), surname.getText(), true);
        Session.getInstance().setAuthenticatedUser(newUser);

        gotoMainMenu();
    }

    @FXML
    void switchToGerman(ActionEvent event) {

        LanguagePropertiesHelper.getInstance().setLanguage("de");
        loadAllTexts();
    }

    @FXML
    void switchToEnglish(ActionEvent event) {

        LanguagePropertiesHelper.getInstance().setLanguage("en");
        loadAllTexts();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllTexts();
    }


    private void loadAllTexts() {

        welcomeHeadline.setText(LanguagePropertiesHelper.getInstance().getWelcomeHeadline());
        welcomeText.setText(LanguagePropertiesHelper.getInstance().getWelcomeText());
        infoHeadline.setText(LanguagePropertiesHelper.getInstance().getInfoHeadline());
        infoText.setText(LanguagePropertiesHelper.getInstance().getInfoText());
        forenameLabel.setText(LanguagePropertiesHelper.getInstance().getForenameLabel());
        surnameLabel.setText(LanguagePropertiesHelper.getInstance().getSurnameLabel());
        userMailLabel.setText(LanguagePropertiesHelper.getInstance().getUserMailLabel());
        userNameLabel.setText(LanguagePropertiesHelper.getInstance().getUserNameLabel());
        pswdLabel.setText(LanguagePropertiesHelper.getInstance().getPswdLabel());
        paswdHintText.setText(LanguagePropertiesHelper.getInstance().getPaswdHintText());
        registrateButton.setText(LanguagePropertiesHelper.getInstance().getRegistrateButton());
    }

    private void gotoMainMenu() {
        try {

            String fxmlPath = "../view/compareSuppliers.fxml";
            FXMLLoader loader = new FXMLLoader(RegistrateCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((CompareSuppliersCtrl)loader.getController()).setStage(stage);

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
