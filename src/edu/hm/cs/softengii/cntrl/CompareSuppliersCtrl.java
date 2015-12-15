/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/

package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.dataStorage.DeliveryRange;
import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.db.sap.Supplier;
import edu.hm.cs.softengii.utils.DeliveryRangeCalculator;
import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.utils.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * JavaFX Controller for 'compareSuppliers.fxml'.
 * It handles the actions triggered in this view.
 *
 * @author Apachen Pub Team
 *
 */
public class CompareSuppliersCtrl implements Initializable {

    private XYChart.Series<String, Number> serie1 = new XYChart.Series<>();
    private XYChart.Series<String, Number> serie2 = new XYChart.Series<>();
    private XYChart.Series<String, Number> serie3 = new XYChart.Series<>();
    private XYChart.Series<String, Number> serie4 = new XYChart.Series<>();
    private DeliveryRangeCalculator rangeCalculator = new DeliveryRangeCalculator();

    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;

    @FXML private SeparatorMenuItem userMenuSeperator;
    @FXML private MenuItem preferencesMenuItem;

    @FXML private SeparatorMenuItem preferencesMenuSeperator;
    @FXML private BarChart<String, Number> compareChart;
    @FXML private ComboBox<Supplier> supplier1Combo;
    @FXML private ComboBox<Supplier> supplier2Combo;
    @FXML private ComboBox<Supplier> supplier3Combo;
	@FXML private ComboBox<Supplier> supplier4Combo;

	@FXML private DatePicker startDatePicker;
	@FXML private DatePicker endDatePicker;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setAdminMenusVisible(Session.getInstance().getAuthenticatedUser().isAdmin());

		compareChart.getData().add(serie1);
		compareChart.getData().add(serie2);
		compareChart.getData().add(serie3);
		compareChart.getData().add(serie4);

		ObservableList<Supplier> list = FXCollections.observableArrayList();

		List<Supplier> suppliers = Database.getInstance().getSupplierData();

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

		// Sort entries alphabetically
		Collections.sort(list, new Comparator<Supplier>() {
			@Override
			public int compare(Supplier s1, Supplier s2) {
				return s1.getName().compareTo(s2.getName());
			}
		});

		supplier1Combo.getStyleClass().add("comboBox-supplier1");
		supplier1Combo.setItems(list);
		supplier1Combo.setConverter(converter);
		supplier2Combo.getStyleClass().add("comboBox-supplier2");
		supplier2Combo.setItems(list);
		supplier2Combo.setConverter(converter);
		supplier3Combo.getStyleClass().add("comboBox-supplier3");
		supplier3Combo.setItems(list);
		supplier3Combo.setConverter(converter);
		supplier4Combo.getStyleClass().add("comboBox-supplier4");
		supplier4Combo.setItems(list);
		supplier4Combo.setConverter(converter);
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
        //Do nothing, we are already here
	}

	@FXML
    public void gotoAverageSuppliers() {
		MenuHelper.getInstance().gotoAverageSuppliers();
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
	void supplier1ComboAction(ActionEvent event) {
		updateChartForAllSuppliers();
	}

	@FXML
	void supplier2ComboAction(ActionEvent event) {
		updateChartForAllSuppliers();
	}

	@FXML
	void supplier3ComboAction(ActionEvent event) {
		updateChartForAllSuppliers();
	}

	@FXML
	void supplier4ComboAction(ActionEvent event) {
		updateChartForAllSuppliers();
	}

	@FXML
	void startDatePickerAction(ActionEvent event) {
		LocalDate dateRangeStart = startDatePicker.getValue();
		rangeCalculator.setRangeStart(dateRangeStart);
		updateChartForAllSuppliers();
	}

	@FXML
	void endDatePickerAction(ActionEvent event) {
		LocalDate dateRangeEnd = endDatePicker.getValue();
		rangeCalculator.setRangeEnd(dateRangeEnd);
		updateChartForAllSuppliers();
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
	 * Replaces old data for all suppliers with the new data.
	 */
	private void updateChartForAllSuppliers() {
		compareChart.getData().clear();

		serie1 = new XYChart.Series<>();
		serie2 = new XYChart.Series<>();
		serie3 = new XYChart.Series<>();
		serie4 = new XYChart.Series<>();

		updateChartForSupplier(supplier1Combo.getValue(), serie1);
		updateChartForSupplier(supplier2Combo.getValue(), serie2);
		updateChartForSupplier(supplier3Combo.getValue(), serie3);
		updateChartForSupplier(supplier4Combo.getValue(), serie4);

		compareChart.getData().addAll(serie1, serie2, serie3, serie4);
	}

	/**
	 * Add the data of the supplier to the serie.
	 * @param supplier
	 * @param serie
	 */
	private void updateChartForSupplier(Supplier supplier, XYChart.Series<String, Number> serie) {

		if (supplier != null) {

			serie.setName(supplier.getName());

			Map<DeliveryRange, Double> ranges = rangeCalculator.calculateDeliveryRanges(supplier);

			
			this.addDeliveryRangeToSerie(serie, ranges, DeliveryRange.VERY_EARLY, "much too early");
			this.addDeliveryRangeToSerie(serie, ranges, DeliveryRange.EARLY, "too early");
			this.addDeliveryRangeToSerie(serie, ranges, DeliveryRange.IN_TIME, "on time");
			this.addDeliveryRangeToSerie(serie, ranges, DeliveryRange.LATE, "too late");
			this.addDeliveryRangeToSerie(serie, ranges, DeliveryRange.VERY_LATE, "much too late");
		}
	}

	/**
	 * Add a range with a label to the serie
	 * @param serie Serie to add
	 * @param ranges All available ranges
	 * @param range Range to add
	 * @param label Label
	 */
	private void addDeliveryRangeToSerie(XYChart.Series<String, Number> serie, 
			Map<DeliveryRange, Double> ranges, DeliveryRange range, String label) {
		
		if (ranges.containsKey(range)) {
			serie.getData().add(new XYChart.Data<String, Number>(label, 100 * ranges.get(range)));
		}
	}
}
