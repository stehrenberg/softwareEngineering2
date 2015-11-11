package edu.hm.cs.softengii.cntrl;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.hm.cs.softengii.db.sap.IDatabase;
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
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.utils.Session;

public class CompareSuppliersCtrl implements Initializable {

	private Stage stage;

	@FXML
	private BarChart<String, Number> compareChart;

	@FXML
	private ComboBox<String> supplier1Combo;

	@FXML
	private ComboBox<String> supplier2Combo;

	@FXML
	private ComboBox<String> supplier3Combo;

	@FXML
	private ComboBox<String> supplier4Combo;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;

	@FXML
	void gotoCreateNewUser(ActionEvent event) {

		try {

			String fxmlPath = "../view/createNewUser.fxml";
			FXMLLoader loader = new FXMLLoader(
					CompareSuppliersCtrl.class.getResource(fxmlPath));

			Parent page = (Parent) loader.load();
			((CreateNewUserCtrl) loader.getController()).setStage(stage);

			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(CompareSuppliersCtrl.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	@FXML
	void gotoManageAllUsers(ActionEvent event) {

		try {

			String fxmlPath = "../view/manageAllUsers.fxml";
			FXMLLoader loader = new FXMLLoader(
					CompareSuppliersCtrl.class.getResource(fxmlPath));

			Parent page = (Parent) loader.load();
			((ManageAllUsersCtrl) loader.getController()).setStage(stage);

			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(CompareSuppliersCtrl.class.getName()).log(
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
					CompareSuppliersCtrl.class.getResource(fxmlPath));

			Parent page = (Parent) loader.load();
			((AverageSuppliersCtrl) loader.getController()).setStage(stage);

			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(CompareSuppliersCtrl.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	@FXML
	void gotoUserSettings(ActionEvent event) {

		try {

			String fxmlPath = "../view/userSettings.fxml";
			FXMLLoader loader = new FXMLLoader(
					CompareSuppliersCtrl.class.getResource(fxmlPath));

			Parent page = (Parent) loader.load();
			((UserSettingsCtrl) loader.getController()).setStage(stage);

			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(CompareSuppliersCtrl.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	@FXML
	void gotoAbout(ActionEvent event) {

		try {

			String fxmlPath = "../view/about.fxml";
			FXMLLoader loader = new FXMLLoader(
					CompareSuppliersCtrl.class.getResource(fxmlPath));

			Parent page = (Parent) loader.load();
			((AboutCtrl) loader.getController()).setStage(stage);

			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(CompareSuppliersCtrl.class.getName()).log(
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

		 ObservableList<String> list = FXCollections.observableArrayList("Supplier 1", "Supplier 2", "Supplier 3");

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
		LocalDate dateRangeStart = startDatePicker.getValue();
		LocalDate dateRangeEnd = endDatePicker.getValue();
		System.out.println("range Start: " + dateRangeStart + " / range End: " + dateRangeEnd);

	}

	@FXML
	void endDatePickerAction(ActionEvent event) {
		LocalDate dateRangeStart = startDatePicker.getValue();
		LocalDate dateRangeEnd = endDatePicker.getValue();
		System.out.println("range Start: " + dateRangeStart + " / range End: " + dateRangeEnd);
	}

	private void filterSupplierData(LocalDate startRange, LocalDate endRange) {
		Database db = Database.getInstance();
		db.getSupplierData();
		db.filterDeliveriesByDate(/*supplier1Combo.getValue()*/"0001000056", startRange, endRange);
		updateChart();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void updateChart() {
		XYChart.Series<String, Number> supplier1 = new XYChart.Series<>();
		supplier1.setName("Supplier 1");
		supplier1.getData().add(new XYChart.Data<String, Number>("very early", 20));
		supplier1.getData().add(new XYChart.Data<String, Number>("early", 40));
		supplier1.getData().add(new XYChart.Data<String, Number>("in time", 90));
		supplier1.getData().add(new XYChart.Data<String, Number>("late", 10));
		supplier1.getData().add(new XYChart.Data<String, Number>("very late", 5));


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
