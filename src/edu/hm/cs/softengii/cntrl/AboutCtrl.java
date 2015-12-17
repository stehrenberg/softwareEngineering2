/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/

package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.utils.LanguagePropertiesHelper;
import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.utils.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX Controller for 'about.fxml'.
 * It handles the actions triggered in this view.
 * @author Apachen Pub Team
 */
public class AboutCtrl implements Initializable {

    @FXML private AnchorPane rootPane;

    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;
    @FXML private SeparatorMenuItem userMenuSeperator;

    @FXML private MenuItem preferencesMenuItem;
    @FXML private SeparatorMenuItem preferencesMenuSeperator;

    @FXML private Text about_developers;
    @FXML private Text git_ref;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAdminMenusVisible(Session.getInstance().getAuthenticatedUser().isAdmin());
        loadAllTexts();
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
        MenuHelper.getInstance().gotoUserSettings();
    }

    @FXML
    public void gotoAbout() {
        //Do nothing, we are already here
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

    private void loadAllTexts() {
        about_developers.setText(LanguagePropertiesHelper.getInstance().getAboutDevelopers());
        git_ref.setText(LanguagePropertiesHelper.getInstance().getGitReference());
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
}
