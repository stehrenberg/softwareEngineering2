package edu.hm.cs.softengii.cntrl;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import edu.hm.cs.softengii.db.sap.SupplierClass;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.db.sap.Supplier;
import edu.hm.cs.softengii.utils.ScoreCalculator;
import edu.hm.cs.softengii.utils.Session;

public class AverageSuppliersCtrl implements Initializable {

	private Stage stage;

    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;
    @FXML private SeparatorMenuItem userMenuSeperator;

	@FXML
	private BarChart<Number, String> compareChart;

	@FXML
	private CheckBox topCheckBox;

	@FXML
	private CheckBox normalCheckBox;

	@FXML
	private CheckBox oneOffCheckBox;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;

	private ScoreCalculator scoreCalculator = new ScoreCalculator();
	private List<SupplierClass> classesToDisplay = new ArrayList<>();

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
		setAdminMenusVisible(Session.getInstance().getAuthenticatedUser().isAdmin());
		topCheckBox.setSelected(false);
		normalCheckBox.setSelected(false);
		oneOffCheckBox.setSelected(false);
		updateChart();
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

	@FXML
	void checkBoxAction(ActionEvent event) {

		classesToDisplay.clear();
		if(topCheckBox.isSelected()) {
			classesToDisplay.add(SupplierClass.TOP);
		}
		if(normalCheckBox.isSelected()) {
			classesToDisplay.add(SupplierClass.NORMAL);
		}
		if(oneOffCheckBox.isSelected()) {
			classesToDisplay.add(SupplierClass.ONE_OFF);
		}
		updateChart();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void updateChart() {

		XYChart.Series<Number, String> serie = new XYChart.Series<>();

		List<Supplier> suppliers = Database.getInstance().getSupplierData();
		List<Supplier> filteredSupps = suppliers.stream()
				.filter(supplier -> classesToDisplay.contains(supplier.getSupplierClass()))
				.collect(Collectors.toList());

		for (Supplier supplier: filteredSupps) {
    		serie.getData().add(new XYChart.Data<Number, String>(scoreCalculator.calculateScore(supplier), supplier.getName()));
    	}

		compareChart.getData().clear();
		compareChart.getData().add(serie);
		for (XYChart.Data<Number, String> data : serie.getData()) {
			displayLabelForData(data);
		}
	}

	/**
	 * places a text label with a bar's value in a bar node for a given
	 * XYChart.Data
	 */
	private void displayLabelForData(XYChart.Data<Number, String> data) {
		final Node node = data.getNode();
		final Text dataText = new Text(data.getXValue() + " %");
		((Group)node.getParent()).getChildren().add(dataText);

		node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
				dataText.setLayoutX(Math.round(bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2));
				dataText.setLayoutY(Math.round(bounds.getMaxY() - bounds.getHeight() / 2 +  dataText.prefHeight(-1) / 2));
			}
		});
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
