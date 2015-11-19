package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.userAuth.DatabaseUserAuth;
import edu.hm.cs.softengii.db.userAuth.UserEntity;
import edu.hm.cs.softengii.utils.Session;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageAllUsersCtrl implements Initializable{

    private Stage stage;
    private UserEntity selectedUser;
    ObservableList<UserEntity> usersObservableList;

    @FXML private AnchorPane rootPane;

    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;
    @FXML private SeparatorMenuItem userMenuSeperator;

    @FXML private MenuItem preferencesMenuItem;
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

    @FXML
    void deleteSelectedUser(ActionEvent event) {

        if(selectedUser == null) {

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

        if(selectedUser == null) {

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
    void gotoCreateNewUser(ActionEvent event) {

        try {

            String fxmlPath = "../view/createNewUser.fxml";
            FXMLLoader loader = new FXMLLoader(ManageAllUsersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((CreateNewUserCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(ManageAllUsersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoManageAllUsers(ActionEvent event) {

        //Do nothing, we are already here
    }

    @FXML
    void gotoCompareSuppliers(ActionEvent event) {

        try {

            String fxmlPath = "../view/compareSuppliers.fxml";
            FXMLLoader loader = new FXMLLoader(ManageAllUsersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((CompareSuppliersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(ManageAllUsersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoAverageSuppliers(ActionEvent event) {

        try {

            String fxmlPath = "../view/averageSuppliers.fxml";
            FXMLLoader loader = new FXMLLoader(ManageAllUsersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((AverageSuppliersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(ManageAllUsersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void gotoUserSettings(ActionEvent event) {

        try {

            String fxmlPath = "../view/userSettings.fxml";
            FXMLLoader loader = new FXMLLoader(ManageAllUsersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((UserSettingsCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(ManageAllUsersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoAbout(ActionEvent event) {

        try {

            String fxmlPath = "../view/about.fxml";
            FXMLLoader loader = new FXMLLoader(ManageAllUsersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((AboutCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(ManageAllUsersCtrl.class.getName()).log(Level.SEVERE, null, ex);
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
    void gotoPreferences(ActionEvent event) {
        try {

            String fxmlPath = "../view/preferences.fxml";
            FXMLLoader loader = new FXMLLoader(
                    ManageAllUsersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((PreferencesCtrl) loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(ManageAllUsersCtrl.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    @FXML
    void quitApplication(ActionEvent event) {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateUsersList();
        setAdminMenusVisible(Session.getInstance().getAuthenticatedUser().isAdmin());

        usersListView.setCellFactory(new Callback<ListView<UserEntity>, ListCell<UserEntity>>() {

            @Override
            public ListCell<UserEntity> call(ListView<UserEntity> p) {

                ListCell<UserEntity> cell = new ListCell<UserEntity>() {

                    @Override
                    protected void updateItem(UserEntity user, boolean empty) {
                        super.updateItem(user, empty);

                        if(empty) {
                            setText(null);
                            setGraphic(null);
                        }else if (user != null) {
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

    private void setAdminMenusVisible(boolean isAdmin) {
        if(isAdmin) {
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
        if(usersListView.getSelectionModel().getSelectedItem() != null) {
            forename.setText(usersListView.getSelectionModel().getSelectedItem().getForename());
            surname.setText(usersListView.getSelectionModel().getSelectedItem().getSurname());
            userMail.setText(usersListView.getSelectionModel().getSelectedItem().getEmail());
            userName.setText(usersListView.getSelectionModel().getSelectedItem().getLoginName());
        }
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
