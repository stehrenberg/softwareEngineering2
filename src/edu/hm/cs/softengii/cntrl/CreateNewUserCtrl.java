package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.userAuth.DatabaseUserAuth;
import edu.hm.cs.softengii.db.userAuth.UserEntity;
import edu.hm.cs.softengii.utils.LanguagePropertiesHelper;
import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JavaFX Controller for 'createNewUser.fxml'.
 * It handles the actions triggered in this view.
 * 
 * @author Apachen Pub Team
 *
 */
public class CreateNewUserCtrl implements Initializable{

    private ArrayList<String> errors = new ArrayList<>();

    @FXML private AnchorPane rootPane;
    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;
    @FXML private SeparatorMenuItem userMenuSeperator;
    @FXML private MenuItem preferencesMenuItem;
    @FXML private SeparatorMenuItem preferencesMenuSeperator;

    @FXML private Text errorMessage;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAdminMenusVisible(Session.getInstance().getAuthenticatedUser().isAdmin());
        errorMessage.setText("");
    }

    @FXML
    void createUser(ActionEvent event) {

        validateInputs();

        if (errors.isEmpty()) {

            UserEntity newUser = DatabaseUserAuth.getInstance().createNewUser(
                userName.getText(),
                pswd.getText(),
                forename.getText(),
                surname.getText(),
                userMail.getText());


            errorMessage.setFill(Color.GREEN);
            errorMessage.setText(String.format("Created new user: %s %s (%s)",
                newUser.getForename(), newUser.getSurname(), newUser.getLoginName()));

            clearAllInputs();
        }
    }

    @FXML
    public void gotoCreateNewUser() {
        //Do nothing, we are already here
    }

    @FXML
    public void gotoManageAllUsers() {
        MenuHelper.getInstance().gotoManageAllUsers();
    }

    @FXML
    public void gotoCompareSuppliers() {
        MenuHelper.getInstance().gotoCompareSuppliers();
    }

    @FXML
    public void gotoAverageSuppliers() {
        MenuHelper.getInstance().gotoAverageSuppliers();
    }

    @FXML
    public void gotoUserSettings() {
        MenuHelper.getInstance().gotoUserSettings();
    }

    @FXML
    public void gotoAbout() {
        MenuHelper.getInstance().gotoAbout();
    }

    @FXML
    public void gotoLogin() {
        MenuHelper.getInstance().gotoLogin();
    }

    @FXML
    public void gotoPreferences() {
        MenuHelper.getInstance().gotoPreferences();
    }

    @FXML
    public void quitApplication() {
        MenuHelper.getInstance().quitApplication();
    }

    /**
     * Decide whether admin menu items should be shown.
     * @param isAdmin
     */
    private void setAdminMenusVisible(boolean isAdmin) {
        if (isAdmin) {
            newUserMenuItem.setVisible(true);
            manageAllUsersMenuItem.setVisible(true);
            userMenuSeperator.setVisible(true);
            preferencesMenuItem.setVisible(true);
            preferencesMenuSeperator.setVisible(true);
        } else {
            newUserMenuItem.setVisible(false);
            manageAllUsersMenuItem.setVisible(false);
            userMenuSeperator.setVisible(false);
            preferencesMenuItem.setVisible(false);
            preferencesMenuSeperator.setVisible(false);
        }
    }

    private void clearAllInputs() {
        forename.setText("");
        surname.setText("");
        userMail.setText("");
        userName.setText("");
        pswd.setText("");
        pswdConfirm.setText("");
    }

    private void clearErrorMessage() {
        errorMessage.setText("");
        errorMessage.setFill(Color.RED);
        if (!errors.isEmpty()) {
            errors.clear();
        }
    }

    private void validateInputs(){

        clearErrorMessage();

        if (forename.getText().isEmpty()) {
            errors.add(LanguagePropertiesHelper.getInstance().getForenameError());
        }

        if (surname.getText().isEmpty()) {
            errors.add(LanguagePropertiesHelper.getInstance().getSurnameError());
        }

        if (userMail.getText().isEmpty()) {
            errors.add(LanguagePropertiesHelper.getInstance().getUserMailEmptyError());
        } else {

            Pattern mailPattern = Pattern.compile("^.+@.+\\..+$");
            Matcher matcher = mailPattern.matcher(userMail.getText());

            if (!matcher.matches()) {
                errors.add(LanguagePropertiesHelper.getInstance().getUserMailError());
            }
        }

        if (userName.getText().isEmpty()) {
            errors.add(LanguagePropertiesHelper.getInstance().getUserNameError());
        } else if (DatabaseUserAuth.getInstance().getUserFromLoginName(userName.getText()) != null) {
            errors.add(LanguagePropertiesHelper.getInstance().getUserNameExistsError());
        }

        if (pswd.getText().isEmpty()){
            errors.add(LanguagePropertiesHelper.getInstance().getPswdError());
        } else if (pswdConfirm.getText().isEmpty()) {
            errors.add(LanguagePropertiesHelper.getInstance().getPswdConfirmError());
        } else if (!pswd.getText().equals(pswdConfirm.getText())) {
            errors.add(LanguagePropertiesHelper.getInstance().getPswdNoMatchError());
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
}
