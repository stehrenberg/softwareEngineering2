package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AverageSuppliersCtrl implements Initializable{

    private Stage stage;

    @FXML private AnchorPane rootPane;
    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;
    @FXML private SeparatorMenuItem userMenuSeperator;

    @FXML private BarChart<String, Number> averageBarChart = new BarChart<String, Number>(new CategoryAxis(), new NumberAxis());

    @FXML
    void gotoCreateNewUser(ActionEvent event) {

        try {

            String fxmlPath = "../view/createNewUser.fxml";
            FXMLLoader loader = new FXMLLoader(AverageSuppliersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((CreateNewUserCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(AverageSuppliersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoManageAllUsers(ActionEvent event) {

        try {

            String fxmlPath = "../view/manageAllUsers.fxml";
            FXMLLoader loader = new FXMLLoader(AverageSuppliersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((ManageAllUsersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(AverageSuppliersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoCompareSuppliers(ActionEvent event) {

        try {

            String fxmlPath = "../view/compareSuppliers.fxml";
            FXMLLoader loader = new FXMLLoader(AverageSuppliersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((CompareSuppliersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(AverageSuppliersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoAverageSuppliers(ActionEvent event) {

        //Do nothing, we are already here
    }

    @FXML
    void gotoUserSettings(ActionEvent event) {

        try {

            String fxmlPath = "../view/userSettings.fxml";
            FXMLLoader loader = new FXMLLoader(AverageSuppliersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((UserSettingsCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(AverageSuppliersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoAbout(ActionEvent event) {

        try {

            String fxmlPath = "../view/about.fxml";
            FXMLLoader loader = new FXMLLoader(AverageSuppliersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((AboutCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(AverageSuppliersCtrl.class.getName()).log(Level.SEVERE, null, ex);
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
    void quitApplication(ActionEvent event) {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAdminMenusVisible(Session.getInstance().getAuthenticatedUser().isAdmin());
        createDemoBarChart();
    }

    private void setAdminMenusVisible(boolean isAdmin) {
        if(isAdmin) {
            newUserMenuItem.setVisible(true);
            manageAllUsersMenuItem.setVisible(true);
            userMenuSeperator.setVisible(true);
        } else {
            newUserMenuItem.setVisible(false);
            manageAllUsersMenuItem.setVisible(false);
            userMenuSeperator.setVisible(false);
        }
    }

    public void createDemoBarChart() {

        XYChart.Series<String, Number> supplier1 = new XYChart.Series<>();
        supplier1.setName("Supplier 1");
        supplier1.getData().add(new XYChart.Data<String, Number>("very early", 20));
        supplier1.getData().add(new XYChart.Data<String, Number>("early", 40));
        supplier1.getData().add(new XYChart.Data<String, Number>("in time", 90));
        supplier1.getData().add(new XYChart.Data<String, Number>("late", 10));
        supplier1.getData().add(new XYChart.Data<String, Number>("very late", 5));

        XYChart.Series<String, Number> supplier2 = new XYChart.Series<>();
        supplier2.setName("Supplier 2");
        supplier2.getData().add(new XYChart.Data<String, Number>("very early", 20));
        supplier2.getData().add(new XYChart.Data<String, Number>("early", 30));
        supplier2.getData().add(new XYChart.Data<String, Number>("in time", 80));
        supplier2.getData().add(new XYChart.Data<String, Number>("late", 20));
        supplier2.getData().add(new XYChart.Data<String, Number>("very late", 10));

        XYChart.Series<String, Number> supplier3 = new XYChart.Series<>();
        supplier3.setName("Supplier 3");
        supplier3.getData().add(new XYChart.Data<String, Number>("very early", 20));
        supplier3.getData().add(new XYChart.Data<String, Number>("early", 30));
        supplier3.getData().add(new XYChart.Data<String, Number>("in time", 80));
        supplier3.getData().add(new XYChart.Data<String, Number>("late", 20));
        supplier3.getData().add(new XYChart.Data<String, Number>("very late", 10));

        averageBarChart.getData().addAll(supplier1, supplier2, supplier3);
        averageBarChart.setLegendVisible(false);
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
