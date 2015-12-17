package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.userAuth.DatabaseUserAuth;
import edu.hm.cs.softengii.db.userAuth.UserEntity;
import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.utils.Session;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * JavaFX Controller for 'manageAllUsers.fxml'.
 * It handles the actions triggered in this view.
 * 
 * @author Apachen Pub Team
 *
 */
public class ManageAllUsersCtrl implements Initializable{

    private UserEntity selectedUser;
    private ObservableList<UserEntity> usersObservableList;

    @FXML private AnchorPane rootPane;

    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;
    @FXML private SeparatorMenuItem userMenuSeperator;
    @FXML private Menu preferencesMenu;
    @FXML private SeparatorMenuItem preferencesMenuSeperator;

    @FXML private ListView<UserEntity> usersListView;
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

    @FXML private Button updateButton;
    @FXML private Button deleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateUsersList();
        setAdminMenusVisible(Session.getInstance().getAuthenticatedUser().isAdmin());

        usersListView.setCellFactory(new Callback<ListView<UserEntity>, ListCell<UserEntity>>() {

            @Override
            public ListCell<UserEntity> call(ListView<UserEntity> p) {

                ListCell<UserEntity> cell = new ListCell<UserEntity>() {

                    @Override
                    public void updateItem(UserEntity user, boolean empty) {
                        super.updateItem(user, empty);

                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else if (user != null) {
                            setText(user.getForename() + " " + user.getSurname() + " (" + user.getLoginName() + ")");
                        }
                    }

                };

                return cell;
            }
        });

        usersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserEntity>() {
            @Override
            public void changed(ObservableValue<? extends UserEntity> observable, UserEntity oldValue, UserEntity newValue) {
                selectedUser = newValue;
                populateInputsFromSelection();
            }
        });
    }

    @FXML
    void deleteSelectedUser(ActionEvent event) {

        if (selectedUser == null) {

            noUserSelectedWaring();

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete User");

            alert.setHeaderText(String.format("Delete selected user:%n\t%s %s (%s)",
                    selectedUser.getForename(),
                    selectedUser.getSurname(),
                    selectedUser.getLoginName()));

            alert.setContentText("Do you want to delete the selected user?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DatabaseUserAuth.getInstance().deleteUserFromLoginName(selectedUser.getLoginName());
                usersObservableList.remove(selectedUser);
            }
        }
    }

    @FXML
    void updateSelectedUser(ActionEvent event) {

        if (selectedUser == null) {

            noUserSelectedWaring();

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Update User");

            alert.setHeaderText(String.format("Update selected user:%n\t%s %s (%s)",
                    selectedUser.getForename(),
                    selectedUser.getSurname(),
                    selectedUser.getLoginName()));

            alert.setContentText("Do you want to update the selected user?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                UserEntity updatedUser = DatabaseUserAuth.getInstance().updateUser(
                    selectedUser.getLoginName(),
                    userName.getText(),
                    pswd.getText(),
                    forename.getText(),
                    surname.getText(),
                    userMail.getText());

                usersObservableList.remove(selectedUser);
                usersObservableList.add(updatedUser);
            }
        }

    }

    @FXML
    public void gotoCreateNewUser() {
       MenuHelper.getInstance().gotoCreateNewUser();
    }

    @FXML
    public void gotoManageAllUsers() {
        //Do nothing, we are already here
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
    public void gotoScorePreferences() {
        MenuHelper.getInstance().gotoScorePreferences();
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
            preferencesMenu.setVisible(true);
            //preferencesMenuItem.setVisible(true);
            preferencesMenuSeperator.setVisible(true);
        } else {
            newUserMenuItem.setVisible(false);
            manageAllUsersMenuItem.setVisible(false);
            userMenuSeperator.setVisible(false);
            preferencesMenu.setVisible(false);
            //preferencesMenuItem.setVisible(false);
            preferencesMenuSeperator.setVisible(false);
        }
    }

    private void noUserSelectedWaring(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No User");
        alert.setHeaderText("No user selected.");
        alert.setContentText("Please select an user to delete or update!");
        alert.showAndWait();
    }

    private void populateUsersList(){
        usersObservableList = FXCollections.observableArrayList();
        for(UserEntity user : DatabaseUserAuth.getInstance().getAllUsers()) {
            if (!user.getLoginName().equals(Session.getInstance().getAuthenticatedUser().getLoginName())) {
                usersObservableList.add(user);
            }
        }
        usersListView.setItems(usersObservableList);
    }

    private void populateInputsFromSelection() {
        if (usersListView.getSelectionModel().getSelectedItem() != null) {
            forename.setText(usersListView.getSelectionModel().getSelectedItem().getForename());
            surname.setText(usersListView.getSelectionModel().getSelectedItem().getSurname());
            userMail.setText(usersListView.getSelectionModel().getSelectedItem().getEmail());
            userName.setText(usersListView.getSelectionModel().getSelectedItem().getLoginName());
        }
    }
}
