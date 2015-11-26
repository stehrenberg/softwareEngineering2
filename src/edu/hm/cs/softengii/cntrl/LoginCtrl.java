package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.userAuth.DatabaseUserAuth;
import edu.hm.cs.softengii.utils.LanguagePropertiesHelper;
import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * JavaFX Controller for 'login.fxml'.
 * It handles the actions triggered in this view.
 * 
 * @author Apachen Pub Team
 *
 */
public class LoginCtrl implements Initializable{

    private ArrayList<String> errors = new ArrayList<>();

    @FXML private Text welcomeHeadline;
    @FXML private Text welcomeText;
    @FXML private Text errorMessage;

    @FXML private TextField userName;
    @FXML private PasswordField pswd;

    @FXML private Button loginButton;
    @FXML private Button germanButton;
    @FXML private Button englishButton;

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

    protected void gotoMainMenu() {
        MenuHelper.getInstance().gotoMainMenu();
    }
}
