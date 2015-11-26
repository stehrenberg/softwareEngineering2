package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.userAuth.DatabaseUserAuth;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JavaFX Controller for 'login.fxml'.
 * It handles the actions triggered in this view.
 * 
 * @author Apachen Pub Team
 *
 */
public class LoginCtrl implements Initializable{

    private Stage stage;

    @FXML private Text welcomeHeadline;
    @FXML private Text welcomeText;
    @FXML private Text errorMessage;

    @FXML private TextField userName;
    @FXML private PasswordField pswd;

    @FXML private Button loginButton;
    @FXML private Button germanButton;
    @FXML private Button englishButton;

    private ArrayList<String> errors = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        loadAllTexts();
    }

    @FXML
    void loginToMainMenu(ActionEvent event) {

        validateInputs();

        if (errors.isEmpty()) {

            Session.getInstance().setAuthenticatedUser(
                DatabaseUserAuth.getInstance().getUserFromLoginName(userName.getText()));
            gotoMainMenu();
        }
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
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }

    private void loadAllTexts() {
        welcomeHeadline.setText(LanguagePropertiesHelper.getInstance().getWelcomeHeadline());
        welcomeText.setText(LanguagePropertiesHelper.getInstance().getWelcomeTextLogin());
        if (!errors.isEmpty()) {
            validateInputs();
        }
    }

    private void clearErrorMessage() {
        errorMessage.setText("");
        if (!errors.isEmpty()) {
            errors.clear();
        }
    }

    private void validateInputs(){

        clearErrorMessage();

        if (userName.getText().isEmpty()) {
            errors.add(LanguagePropertiesHelper.getInstance().getUserNameError());
        }

        if (pswd.getText().isEmpty()){
            errors.add(LanguagePropertiesHelper.getInstance().getPswdError());
        }

        if (!userName.getText().isEmpty() && !pswd.getText().isEmpty() && !isCorrectCredentials()) {
            errors.add(LanguagePropertiesHelper.getInstance().getLoginError());
        }

        if (!errors.isEmpty()) {
            populateErrorMessage();
        }

    }


    private void populateErrorMessage() {
        if (!errors.isEmpty()){
            for (String error : errors) {
                errorMessage.setText(errorMessage.getText() + error);
            }
        }
    }

    private boolean isCorrectCredentials(){
        return DatabaseUserAuth.getInstance().isLoginCorrect(userName.getText(), pswd.getText());
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
