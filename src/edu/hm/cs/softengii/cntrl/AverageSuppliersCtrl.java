/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/

package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.db.sap.Supplier;
import edu.hm.cs.softengii.db.sap.SupplierClass;
import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.utils.ScoreCalculator;
import edu.hm.cs.softengii.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * JavaFX Controller for 'averageSuppliers.fxml'.
 * It handles the actions triggered in this view.
 *
 * @author Apachen Pub Team
 *
 */
public class AverageSuppliersCtrl implements Initializable {

    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;

    @FXML private SeparatorMenuItem userMenuSeperator;
    @FXML private MenuItem preferencesMenuItem;

	@FXML private SeparatorMenuItem preferencesMenuSeperator;

	@FXML private BarChart<Number, String> compareChart;

	@FXML private CheckBox topCheckBox;

	@FXML private CheckBox normalCheckBox;

	@FXML private CheckBox oneOffCheckBox;

	@FXML private DatePicker startDatePicker;

	@FXML private DatePicker endDatePicker;

	private ScoreCalculator scoreCalculator = new ScoreCalculator();
	/** Contains all supplier classes that are selected via checkbox to be displayed. */
	private List<SupplierClass> classesToDisplay = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setAdminMenusVisible(Session.getInstance().getAuthenticatedUser().isAdmin());
		updateChart();
	}

	@FXML
    public void gotoCreateNewUser() {
		MenuHelper.getInstance().gotoCreateNewUser();
	}

	@FXML
    public void gotoManageAllUsers() {
		MenuHelper.getInstance().gotoManageAllUsers();
	}

	@FXML
    public void gotoCompareSuppliers() {
		MenuHelper.getInstance().gotoCompareSuppliers();
	}

	@FXML
    public void gotoAverageSuppliers() {
        //Do nothing, we are already here
	}

	@FXML
    public void gotoUserSettings() {
		MenuHelper.getInstance().gotoUserSettings();
	}

	@FXML
    public void gotoAbout() {
		MenuHelper.getInstance().gotoAbout();
	}

	@FXML
    public void gotoLogin() {
		MenuHelper.getInstance().gotoLogin();
	}

	@FXML
    public void gotoPreferences() {
		MenuHelper.getInstance().gotoPreferences();
	}

    @FXML
    public void quitApplication() {
        MenuHelper.getInstance().quitApplication();
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
		if (topCheckBox.isSelected()) {
			classesToDisplay.add(SupplierClass.TOP);
		}
		if (normalCheckBox.isSelected()) {
			classesToDisplay.add(SupplierClass.NORMAL);
		}
		if (oneOffCheckBox.isSelected()) {
			classesToDisplay.add(SupplierClass.ONE_OFF);
		}
		updateChart();
	}

	/**
	 * Decide whether admin menu items should be shown.
	 * @param isAdmin
	 */
	private void setAdminMenusVisible(boolean isAdmin) {
		if (isAdmin) {
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

	/**
	 * Clear chart and insert new data.
	 */
	private void updateChart() {

		XYChart.Series<Number, String> serie = new XYChart.Series<>();

		List<Supplier> suppliers = Database.getInstance().getSupplierData();
		List<Supplier> filteredSupps = suppliers.stream()
		    .filter(supplier -> classesToDisplay.contains(supplier.getSupplierClass()))
		    .collect(Collectors.toList());
		
		for (Supplier supplier: filteredSupps) {

			double score = scoreCalculator.calculateScore(supplier);
			double rounded = ((int)(score * 100)) / 100.0;

    		serie.getData().add(new XYChart.Data<Number, String>(score, supplier.getName() + " "
    		    + rounded + "%\n" + supplier.getSupplierClass()));
    	}

		// Sort entries by score value
		Collections.sort(serie.getData(), new Comparator<XYChart.Data<Number, String>>() {
			@Override
			public int compare(XYChart.Data<Number, String> s1, XYChart.Data<Number, String> s2) {
				return new BigDecimal(s1.getXValue().toString()).compareTo(new BigDecimal(s2.getXValue().toString()));
			}
		});

		compareChart.getData().clear();
		compareChart.getData().add(serie);

		compareChart.setMinHeight(filteredSupps.size() * 50);
	}
}
