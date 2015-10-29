package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.daos.UserDao;
import edu.hm.cs.softengii.db.entities.UserEntity;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuCtrl implements Initializable{

    private Stage stage;

    @FXML private Text greeting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UserDao userDao = new UserDao();
        String firstUserName = "";

        if(!userDao.getAllUserNames().isEmpty()) {
          firstUserName = (String) userDao.getAllUserNames().get(0);
        } else {
            System.out.println("no users found!");
        }


        greeting.setText("Hello " + firstUserName + "!");

    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
