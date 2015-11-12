package edu.hm.cs.softengii.cntrl;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import edu.hm.cs.softengii.utils.Session;

public class AverageSuppliersCtrl implements Initializable {

	private Stage stage;

	@FXML
	private BarChart<Number, String> compareChart;

	@FXML
	private ComboBox<String> supplier1Combo;

	@FXML
	private ComboBox<String> supplier2Combo;

	@FXML
	private ComboBox<String> supplier3Combo;

	@FXML
	private ComboBox<String> supplier4Combo;

	@FXML
	void gotoCreateNewUser(ActionEvent event) {

		try {

			String fxmlPath = "../view/createNewUser.fxml";
			FXMLLoader loader = new FXMLLoader(
					AverageSuppliersCtrl.class.getResource(fxmlPath));

			Parent page = (Parent) loader.load();
			((CreateNewUserCtrl) loader.getController()).setStage(stage);

			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(AverageSuppliersCtrl.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	@FXML
	void gotoManageAllUsers(ActionEvent event) {

		try {

			String fxmlPath = "../view/manageAllUsers.fxml";
			FXMLLoader loader = new FXMLLoader(
					AverageSuppliersCtrl.class.getResource(fxmlPath));

			Parent page = (Parent) loader.load();
			((ManageAllUsersCtrl) loader.getController()).setStage(stage);

			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(AverageSuppliersCtrl.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	@FXML
	void gotoCompareSuppliers(ActionEvent event) {

		// Do nothing, we are already here
	}

	@FXML
	void gotoAverageSuppliers(ActionEvent event) {

		try {

			String fxmlPath = "../view/averageSuppliers.fxml";
			FXMLLoader loader = new FXMLLoader(
					AverageSuppliersCtrl.class.getResource(fxmlPath));

			Parent page = (Parent) loader.load();
			((AverageSuppliersCtrl) loader.getController()).setStage(stage);

			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(AverageSuppliersCtrl.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	@FXML
	void gotoUserSettings(ActionEvent event) {

		try {

			String fxmlPath = "../view/userSettings.fxml";
			FXMLLoader loader = new FXMLLoader(
					AverageSuppliersCtrl.class.getResource(fxmlPath));

			Parent page = (Parent) loader.load();
			((UserSettingsCtrl) loader.getController()).setStage(stage);

			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(AverageSuppliersCtrl.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	@FXML
	void gotoAbout(ActionEvent event) {

		try {

			String fxmlPath = "../view/about.fxml";
			FXMLLoader loader = new FXMLLoader(
					AverageSuppliersCtrl.class.getResource(fxmlPath));

			Parent page = (Parent) loader.load();
			((AboutCtrl) loader.getController()).setStage(stage);

			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(AverageSuppliersCtrl.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	@FXML
	void gotoLogin(ActionEvent event) {

		Session.getInstance().close();

		try {

			String fxmlPath = "../view/login.fxml";
			FXMLLoader loader = new FXMLLoader(
					UserSettingsCtrl.class.getResource(fxmlPath));

			Parent page = (Parent) loader.load();
			((LoginCtrl) loader.getController()).setStage(stage);

			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(UserSettingsCtrl.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	@FXML
	void quitApplication(ActionEvent event) {
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		 ObservableList<String> list = FXCollections.observableArrayList("Supplier 1", "Supplier 2", "Supplier 3", "Supplier 4");

		// ObservableList<String> list =
		// FXCollections.observableArrayList(Database.getInstance().getSuppliers());
		 supplier1Combo.setItems(list);
		 supplier2Combo.setItems(list);
		 supplier3Combo.setItems(list);
		 supplier4Combo.setItems(list);
	}

	@FXML
	void supplier1ComboAction(ActionEvent event) {
		System.out.println("Supplier1Combo");
		updateChart();
	}

	@FXML
	void supplier2ComboAction(ActionEvent event) {
		System.out.println("Supplier2Combo");
		updateChart();
	}

	@FXML
	void supplier3ComboAction(ActionEvent event) {
		System.out.println("Supplier3Combo");
		updateChart();
	}

	@FXML
	void supplier4ComboAction(ActionEvent event) {
		System.out.println("Supplier4Combo");
		updateChart();
	}
	
	@FXML
	void startDatePickerAction(ActionEvent event) {
		System.out.println("startDatePicker");
	}

	@FXML
	void endDatePickerAction(ActionEvent event) {
		System.out.println("endDatePicker");
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void updateChart() {
		XYChart.Series<Number, String> supplier1 = new XYChart.Series<>();
		
		
		
		if (supplier1Combo.getValue() != null) {
			supplier1.getData().add(new XYChart.Data<Number, String>(100, supplier1Combo.getValue()));			
		}
		if (supplier2Combo.getValue() != null) {
			supplier1.getData().add(new XYChart.Data<Number, String>(90, supplier2Combo.getValue()));			
		}
		if (supplier3Combo.getValue() != null) {
			supplier1.getData().add(new XYChart.Data<Number, String>(80, supplier3Combo.getValue()));			
		}
		if (supplier4Combo.getValue() != null) {
			supplier1.getData().add(new XYChart.Data<Number, String>(70, supplier4Combo.getValue()));			
		}

		compareChart.getData().clear();
		compareChart.getData().add(supplier1);
	}

	private Parent replaceSceneContent(Parent page) throws Exception {

		Scene scene = stage.getScene();
		if (scene == null) {
			scene = new Scene(page, 640, 480);

			// TODO no CSS yet
			// scene.getStylesheets().add(App.class.getResource("demo.css").toExternalForm());

			stage.setScene(scene);
		} else {
			stage.getScene().setRoot(page);
		}
		stage.sizeToScene();
		return page;
	}

}
