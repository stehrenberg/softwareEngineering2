package edu.hm.cs.softengii.cntrl;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.db.sap.Supplier;
import edu.hm.cs.softengii.utils.ScoreCalculator;
import edu.hm.cs.softengii.utils.Session;

public class AverageSuppliersCtrl implements Initializable {

	private Stage stage;

	@FXML
	private BarChart<Number, String> compareChart;

	@FXML
	private ComboBox<Supplier> supplier1Combo;

	@FXML
	private ComboBox<Supplier> supplier2Combo;

	@FXML
	private ComboBox<Supplier> supplier3Combo;

	@FXML
	private ComboBox<Supplier> supplier4Combo;
	
//	XYChart.Series<Number, String> serie1 = new XYChart.Series<>();
//
//	XYChart.Series<Number, String> serie2 = new XYChart.Series<>();
//
//	XYChart.Series<Number, String> serie3 = new XYChart.Series<>();
//
//	XYChart.Series<Number, String> serie4 = new XYChart.Series<>();
	
	XYChart.Series<Number, String> serie = new XYChart.Series<>();

	ScoreCalculator scoreCalculator = new ScoreCalculator();
	
	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;
	
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
		// Do nothing, we are already here
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

//		compareChart.getData().add(serie1);
//		compareChart.getData().add(serie2);
//		compareChart.getData().add(serie3);
//		compareChart.getData().add(serie4);

		 ObservableList<Supplier> list = FXCollections.observableArrayList();

//		 // Add null for nothing to select
//		 list.add(null);

		 ArrayList<Supplier> suppliers = Database.getInstance().getSupplierData();

		 for (int i = 0; i < suppliers.size(); i++) {
			 list.add(suppliers.get(i));
		 }

		 StringConverter<Supplier> converter = new StringConverter<Supplier>() {
		    @Override
		    public String toString(Supplier supplier) {
		        if (supplier == null) {
		            return null;
		        } else {
		            return supplier.getName();
		        }
		    }

		    @Override
		    public Supplier fromString(String supplierString) {
		        return null; // No conversion fromString needed.
		    }
		 };

		 supplier1Combo.setItems(list);
		 supplier1Combo.setConverter(converter);
		 supplier2Combo.setItems(list);
		 supplier2Combo.setConverter(converter);
		 supplier3Combo.setItems(list);
		 supplier3Combo.setConverter(converter);
		 supplier4Combo.setItems(list);
		 supplier4Combo.setConverter(converter);
	}

	@FXML
	void supplier1ComboAction(ActionEvent event) {
		System.out.println("Supplier1Combo");
		updateChart();
//		addSupplierScoreToSerie(supplier1Combo.getValue(), serie);
	}

	@FXML
	void supplier2ComboAction(ActionEvent event) {
		System.out.println("Supplier2Combo");
		updateChart();
//		addSupplierScoreToSerie(supplier2Combo.getValue(), serie);
	}

	@FXML
	void supplier3ComboAction(ActionEvent event) {
		System.out.println("Supplier3Combo");
		updateChart();
//		addSupplierScoreToSerie(supplier3Combo.getValue(), serie);
	}

	@FXML
	void supplier4ComboAction(ActionEvent event) {
		System.out.println("Supplier4Combo");
		updateChart();
//		addSupplierScoreToSerie(supplier4Combo.getValue(), serie);
	}
	
	@FXML
	void startDatePickerAction(ActionEvent event) {
		LocalDate dateRangeStart = startDatePicker.getValue();
		scoreCalculator.setRangeStart(dateRangeStart);
		updateChart();
	}

	@FXML
	void endDatePickerAction(ActionEvent event) {
		LocalDate dateRangeEnd = endDatePicker.getValue();
		scoreCalculator.setRangeEnd(dateRangeEnd);
		updateChart();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void updateChart() {		
		
		serie = new XYChart.Series<>();
		
	
		addSupplierScoreToSerie(supplier1Combo.getValue());
		addSupplierScoreToSerie(supplier2Combo.getValue());
		addSupplierScoreToSerie(supplier3Combo.getValue());
		addSupplierScoreToSerie(supplier4Combo.getValue());
		
		compareChart.getData().clear();
		compareChart.getData().add(serie);
		
	}

	private void addSupplierScoreToSerie(Supplier supplier) {

		if (supplier != null) {
			serie.getData().add(new XYChart.Data<Number, String>(scoreCalculator.calculateScore(supplier), supplier.getName()));			
		}
		
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
