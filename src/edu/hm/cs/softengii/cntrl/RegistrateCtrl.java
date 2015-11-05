package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.userAuth.DatabaseUserAuth;
import edu.hm.cs.softengii.db.userAuth.UserEntity;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrateCtrl implements Initializable{

    private Stage stage;
    private ArrayList<String> errors = new ArrayList<>();

    @FXML private Text welcomeHeadline;
    @FXML private Text welcomeText;
    @FXML private Text infoHeadline;
    @FXML private Text infoText;
    @FXML private Text errorMessageLeft;
    @FXML private Text errorMessageRight;

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

        validateInputs();

        if (errors.isEmpty()) {

            UserEntity newUser = DatabaseUserAuth.getInstance().createNewAdminUser(
                    userName.getText(),
                    pswd.getText(),
                    forename.getText(),
                    surname.getText(),
                    userMail.getText());

            Session.getInstance().setAuthenticatedUser(newUser);
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearErrorMessage();
        loadAllTexts();
    }


    private void clearErrorMessage() {
        errorMessageLeft.setText("");
        errorMessageRight.setText("");
        if(!errors.isEmpty()) {
            errors.clear();
        }
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
        if(!errors.isEmpty()) {
            validateInputs();
        }
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

    private void validateInputs(){

        clearErrorMessage();

        if(forename.getText().isEmpty()) {
            errors.add(LanguagePropertiesHelper.getInstance().getForenameError());
        }

        if(surname.getText().isEmpty()) {
            errors.add(LanguagePropertiesHelper.getInstance().getSurnameError());
        }

        if(userMail.getText().isEmpty()) {
            errors.add(LanguagePropertiesHelper.getInstance().getUserMailEmptyError());
        } else {

            Pattern mailPattern = Pattern.compile("^.+@.+\\..+$");
            Matcher matcher = mailPattern.matcher(userMail.getText());

            if(!matcher.matches()) {
                errors.add(LanguagePropertiesHelper.getInstance().getUserMailError());
            }
        }

        if(userName.getText().isEmpty()) {
            errors.add(LanguagePropertiesHelper.getInstance().getUserNameError());
        } else if(DatabaseUserAuth.getInstance().getUserFromLoginName(userName.getText()) != null) {
                errors.add(LanguagePropertiesHelper.getInstance().getUserNameExistsError());
        }

        if(pswd.getText().isEmpty()){
            errors.add(LanguagePropertiesHelper.getInstance().getPswdError());
        } else if (pswdConfirm.getText().isEmpty()) {
            errors.add(LanguagePropertiesHelper.getInstance().getPswdConfirmError());
        } else if (!pswd.getText().equals(pswdConfirm.getText())) {
            errors.add(LanguagePropertiesHelper.getInstance().getPswdNoMatchError());
        }

        if(!errors.isEmpty()) {
            populateErrorMessage();
        }
    }

    private void populateErrorMessage() {

        if(!errors.isEmpty() && errors.size() > 3) {
            for (int i = 0; i < errors.size(); i++) {
                if(i < 3) {
                    errorMessageLeft.setText(errorMessageLeft.getText() + errors.get(i));
                } else {
                    errorMessageRight.setText(errorMessageRight.getText() + errors.get(i));
                }
            }
        } else if (!errors.isEmpty()){
            for (String error : errors) {
                errorMessageLeft.setText(errorMessageLeft.getText() + error);
            }
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
