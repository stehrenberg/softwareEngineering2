package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.db.sap.IDatabase;
import edu.hm.cs.softengii.db.sap.Supplier;
import edu.hm.cs.softengii.utils.ScoreCalculator;
import edu.hm.cs.softengii.utils.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompareSuppliersCtrl implements Initializable{

    private Stage stage;

    @FXML private AnchorPane rootPane;
    @FXML private ListView<String> suppliersListView;
    @FXML private TableView<SupplierScoreViewModel> suppliersTableView;

    @FXML
    void gotoCreateNewUser(ActionEvent event) {

        try {

            String fxmlPath = "../view/createNewUser.fxml";
            FXMLLoader loader = new FXMLLoader(CompareSuppliersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((CreateNewUserCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(CompareSuppliersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoManageAllUsers(ActionEvent event) {

        try {

            String fxmlPath = "../view/manageAllUsers.fxml";
            FXMLLoader loader = new FXMLLoader(CompareSuppliersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((ManageAllUsersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(CompareSuppliersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoCompareSuppliers(ActionEvent event) {

        //Do nothing, we are already here
    }

    @FXML
    void gotoAverageSuppliers(ActionEvent event) {

        try {

            String fxmlPath = "../view/averageSuppliers.fxml";
            FXMLLoader loader = new FXMLLoader(CompareSuppliersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((AverageSuppliersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(CompareSuppliersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoUserSettings(ActionEvent event) {

        try {

            String fxmlPath = "../view/userSettings.fxml";
            FXMLLoader loader = new FXMLLoader(CompareSuppliersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((UserSettingsCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(CompareSuppliersCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoAbout(ActionEvent event) {

        try {

            String fxmlPath = "../view/about.fxml";
            FXMLLoader loader = new FXMLLoader(CompareSuppliersCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((AboutCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(CompareSuppliersCtrl.class.getName()).log(Level.SEVERE, null, ex);
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

    	ObservableList<SupplierScoreViewModel> list = FXCollections.observableArrayList();
    	
    	ScoreCalculator scoreCalculator = new ScoreCalculator();
    	
    	IDatabase db = Database.getInstance();
    	ArrayList<Supplier> suppliers = db.getSupplierData();
    	
    	for (Supplier supplier: suppliers) {
    		double score = scoreCalculator.calculateScore(supplier);
			list.add(new SupplierScoreViewModel(supplier.getName(), score));
    	}
    	
    	Collections.sort(list, new Comparator<SupplierScoreViewModel>() {
    	    @Override
    	    public int compare(SupplierScoreViewModel s1, SupplierScoreViewModel s2) {
    	        return - Double.compare(s1.getScoreValue(), s2.getScoreValue());
    	    }
    	});
		      
        TableColumn supplierColumn = new TableColumn("Supplier");
        supplierColumn.setMinWidth(300);
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        
        TableColumn scoreColumn = new TableColumn("Score");
        scoreColumn.setMinWidth(50);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        
        suppliersTableView.getColumns().addAll(supplierColumn, scoreColumn);
        
        suppliersTableView.setItems(list);


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

    public static class SupplierScoreViewModel {
    	
    	private final SimpleStringProperty supplierName;
        private final SimpleStringProperty score;
        
        private double scoreValue;

        public SupplierScoreViewModel(String supplierName, double score) {
        	
        	this.scoreValue = score;
            this.supplierName = new SimpleStringProperty(supplierName);
            this.score = new SimpleStringProperty(String.valueOf(Math.round(score)) + "%");
        }
        
        public String getSupplierName() {
        	return supplierName.get();
        }
        
        public String getScore() {
            return score.get();
        }
        
        public double getScoreValue() {
        	return scoreValue;
        }
        
        public void setScore(double score) {
        	this.scoreValue = score;
        	this.score.set(String.valueOf(Math.round(score)) + "%");
        }
        
        public void setSupplierName(String supplierName) {
        	this.supplierName.set(supplierName);
        }
    }
}
