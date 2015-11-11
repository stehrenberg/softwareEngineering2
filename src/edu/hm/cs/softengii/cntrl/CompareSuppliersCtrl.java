package edu.hm.cs.softengii.cntrl;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.hm.cs.softengii.db.sap.IDatabase;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.db.sap.DemoDatabase;
import edu.hm.cs.softengii.db.sap.Supplier;
import edu.hm.cs.softengii.utils.DeliveryRangeCalculator;
import edu.hm.cs.softengii.utils.DeliveryRangeCalculator.Range;
import edu.hm.cs.softengii.utils.Session;

public class CompareSuppliersCtrl implements Initializable {

	private Stage stage;

	@FXML
	private BarChart<String, Number> compareChart;

	@FXML
	private ComboBox<Supplier> supplier1Combo;

	@FXML
	private ComboBox<Supplier> supplier2Combo;

	@FXML
	private ComboBox<Supplier> supplier3Combo;

	@FXML
	private ComboBox<Supplier> supplier4Combo;

	XYChart.Series<String, Number> serie1 = new XYChart.Series<>();

	XYChart.Series<String, Number> serie2 = new XYChart.Series<>();

	XYChart.Series<String, Number> serie3 = new XYChart.Series<>();

	XYChart.Series<String, Number> serie4 = new XYChart.Series<>();

	DeliveryRangeCalculator rangeCalculator = new DeliveryRangeCalculator();

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

		compareChart.getData().add(serie1);
		compareChart.getData().add(serie2);
		compareChart.getData().add(serie3);
		compareChart.getData().add(serie4);

		 ObservableList<Supplier> list = FXCollections.observableArrayList();

		 // Add null for nothing to select
		 list.add(null);

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
		//updateChart();
		updateChartForSupplier(supplier1Combo.getValue(), serie1);
	}

	@FXML
	void supplier2ComboAction(ActionEvent event) {
		System.out.println("Supplier2Combo");
		//updateChart();
		updateChartForSupplier(supplier2Combo.getValue(), serie2);
	}

	@FXML
	void supplier3ComboAction(ActionEvent event) {
		System.out.println("Supplier3Combo");
		//updateChart();
		updateChartForSupplier(supplier3Combo.getValue(), serie3);
	}

	@FXML
	void supplier4ComboAction(ActionEvent event) {
		System.out.println("Supplier4Combo");
		//updateChart();
		updateChartForSupplier(supplier4Combo.getValue(), serie4);
	}

	@FXML
	void startDatePickerAction(ActionEvent event) {
		LocalDate dateRangeStart = startDatePicker.getValue();
		rangeCalculator.setRangeStart(dateRangeStart);
	}

	@FXML
	void endDatePickerAction(ActionEvent event) {
		LocalDate dateRangeEnd = endDatePicker.getValue();
		rangeCalculator.setRangeEnd(dateRangeEnd);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void updateChartForSupplier(Supplier supplier, XYChart.Series<String, Number> serie) {

		// First remove all data from this serie
		serie.getData().clear();

		if (supplier != null) {

			// Change serie data for this supplier
			serie.setName(supplier.getName());

			Map<Range, Double> ranges = rangeCalculator.calculateDeliveryRanges(supplier);

			serie.getData().add(new XYChart.Data<String, Number>("very early", 100* ranges.get(Range.VERY_EARLY)));
			serie.getData().add(new XYChart.Data<String, Number>("early", 100 * ranges.get(Range.EARLY)));
			serie.getData().add(new XYChart.Data<String, Number>("in time", 100 * ranges.get(Range.IN_TIME)));
			serie.getData().add(new XYChart.Data<String, Number>("late", 100 * ranges.get(Range.LATE)));
			serie.getData().add(new XYChart.Data<String, Number>("very late", 100 * ranges.get(Range.VERY_LATE)));
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
