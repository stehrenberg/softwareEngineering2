package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.userAuth.DatabaseUserAuth;
import edu.hm.cs.softengii.db.userAuth.UserEntity;
import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * JavaFX Controller for 'userSettings.fxml'.
 * It handles the actions triggered in this view.
 * 
 * @author Apachen Pub Team
 *
 */
public class UserSettingsCtrl implements Initializable{

    @FXML private AnchorPane rootPane;

    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;
    @FXML private SeparatorMenuItem userMenuSeperator;

    @FXML private MenuItem preferencesMenuItem;
    @FXML private SeparatorMenuItem preferencesMenuSeperator;

    @FXML private Text forenameLabel;
    @FXML private TextField forename;
    @FXML private Text surnameLabel;
    @FXML private TextField surname;
    @FXML private Text userMailLabel;
    @FXML private TextField userMail;
    @FXML private Text userNameLabel;
    @FXML private TextField userName;
    @FXML private Text isAdminLabel;
    @FXML private CheckBox isAdmin;
    @FXML private Text pswdLabel;
    @FXML private Text paswdHintText;
    @FXML private PasswordField pswd;
    @FXML private PasswordField pswdConfirm;

    @FXML private Button updateButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateInputs();
        setAdminMenusVisible(Session.getInstance().getAuthenticatedUser().isAdmin());
    }

    @FXML
    void updateUser(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update User");

        alert.setHeaderText("Update user account settings.");

        alert.setContentText("Do you want to update your account settings now?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            UserEntity updatedUser = DatabaseUserAuth.getInstance().updateUser(
                Session.getInstance().getAuthenticatedUser().getLoginName(),
                userName.getText(),
                pswd.getText(),
                forename.getText(),
                surname.getText(),
                userMail.getText());

            Session.getInstance().setAuthenticatedUser(updatedUser);
            populateInputs();
        }

    }

    @FXML
    public void gotoCreateNewUser() {
        MenuHelper.getInstance().gotoCreateNewUser();
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
        //Do nothing, we are already here
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
            isAdminLabel.setVisible(true);
            this.isAdmin.setVisible(true);
            newUserMenuItem.setVisible(true);
            manageAllUsersMenuItem.setVisible(true);
            userMenuSeperator.setVisible(true);
            preferencesMenuItem.setVisible(true);
            preferencesMenuSeperator.setVisible(true);
        } else {
            isAdminLabel.setVisible(false);
            this.isAdmin.setVisible(false);
            newUserMenuItem.setVisible(false);
            manageAllUsersMenuItem.setVisible(false);
            userMenuSeperator.setVisible(false);
            preferencesMenuItem.setVisible(false);
            preferencesMenuSeperator.setVisible(false);
        }
    }

    private void populateInputs() {
        forename.setText(Session.getInstance().getAuthenticatedUser().getForename());
        surname.setText(Session.getInstance().getAuthenticatedUser().getSurname());
        userMail.setText(Session.getInstance().getAuthenticatedUser().getEmail());
        userName.setText(Session.getInstance().getAuthenticatedUser().getLoginName());
        isAdmin.setSelected(Session.getInstance().getAuthenticatedUser().isAdmin());
    }
}
