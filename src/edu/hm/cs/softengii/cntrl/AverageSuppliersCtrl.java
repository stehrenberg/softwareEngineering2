/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/

package edu.hm.cs.softengii.cntrl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

import edu.hm.cs.softengii.db.dataStorage.SupplierClass;
import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.db.sap.Supplier;
import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.utils.ScoreCalculator;
import edu.hm.cs.softengii.utils.Session;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.DirectoryChooser;

/**
 * JavaFX Controller for 'averageSuppliers.fxml'.
 * It handles the actions triggered in this view.
 *
 * @author Apachen Pub Team
 *
 */
public class AverageSuppliersCtrl implements Initializable {
	
	private final static int PAGE_HEIGHT = 8010;

    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;

    @FXML private SeparatorMenuItem userMenuSeperator;
	@FXML private Menu preferencesMenu;
	@FXML private SeparatorMenuItem preferencesMenuSeperator;

	@FXML private BarChart<Number, String> compareChart;

	@FXML private CheckBox topCheckBox;

	@FXML private CheckBox normalCheckBox;

	@FXML private CheckBox oneOffCheckBox;

	@FXML private DatePicker startDatePicker;

	@FXML private DatePicker endDatePicker;

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
	public void gotoScorePreferences() {
		MenuHelper.getInstance().gotoScorePreferences();
	}

	@FXML
	public void gotoDeliveryPreferences() {
		MenuHelper.getInstance().gotoDeliveryPreferences();
	}

    @FXML
    public void quitApplication() {
        MenuHelper.getInstance().quitApplication();
    }

    @FXML
	void startDatePickerAction(ActionEvent event) {
		updateChart();
	}

	@FXML
	void endDatePickerAction(ActionEvent event) {
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
	
	@FXML
    void exportPDF(ActionEvent event) {
	    Scene scene = compareChart.getScene();
	    int widthScene = (int)scene.getWidth();
	    int heightScene = (int)scene.getHeight();
        
        Rectangle page = new Rectangle(widthScene, heightScene);
        Document document = new Document(page);
	    WritableImage writeableScene = scene.snapshot(new WritableImage(widthScene, heightScene));
        ByteArrayOutputStream byteOutputScene = new ByteArrayOutputStream();
        PdfWriter writer = null;
        
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose an export directory");
        File selectedDirectory = chooser.showDialog(scene.getWindow());
        
        try {
        	String path = "";
        	if(selectedDirectory.isDirectory()) {
        		path = selectedDirectory.getAbsolutePath();
        	} else {
        		path = getClass().getProtectionDomain().getCodeSource().getLocation().getHost();
        	}
            writer = PdfWriter.getInstance(document, new FileOutputStream(path + "/AverageSuppliers.pdf"));
            document.open();
            ImageIO.write(SwingFXUtils.fromFXImage(writeableScene, null),"png", byteOutputScene);
            Image imageScene = Image.getInstance(byteOutputScene.toByteArray());
            
            imageScene.setAbsolutePosition(0, 0);
            document.add(imageScene);
            document.add(imageScene);
            
            int compareChartheight = (int)compareChart.getHeight();
            int width = (int)compareChart.getWidth();
            
            for(int currentHeight = 0; currentHeight < compareChartheight; currentHeight += PAGE_HEIGHT) {
            	SnapshotParameters parameters = new SnapshotParameters();
                parameters.setViewport(new Rectangle2D(0, currentHeight, width, PAGE_HEIGHT));
        	    
                WritableImage writeableGraph = compareChart.snapshot(parameters, null);
                ByteArrayOutputStream byteOutputGraph = new ByteArrayOutputStream();
                ImageIO.write(SwingFXUtils.fromFXImage(writeableGraph, null),"png", byteOutputGraph);
                Image imageGraph = Image.getInstance(byteOutputGraph.toByteArray());
            	
                if(currentHeight + PAGE_HEIGHT > compareChartheight) {
                	document.setPageSize(new Rectangle((float)width + 100, (float)compareChartheight - currentHeight + 100));
                }
                else {
                	document.setPageSize(new Rectangle((float)width + 100, (float)PAGE_HEIGHT + 100));
                }
            	document.newPage();
            	document.add(imageGraph);
            }
            
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        document.close();
        writer.close();
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
			preferencesMenu.setVisible(true);
			//preferencesMenuItem.setVisible(true);
			preferencesMenuSeperator.setVisible(true);
		} else {
			newUserMenuItem.setVisible(false);
			manageAllUsersMenuItem.setVisible(false);
			userMenuSeperator.setVisible(false);
			preferencesMenu.setVisible(false);
			//preferencesMenuItem.setVisible(false);
			preferencesMenuSeperator.setVisible(false);
		}
	}

	/**
	 * Clear chart and insert new data.
	 */
	private void updateChart() {

		ScoreCalculator scoreCalculator = new ScoreCalculator();
		
		if (startDatePicker.getValue() != null) {
			scoreCalculator.setRangeStart(startDatePicker.getValue());
		}
		
		if (endDatePicker.getValue() != null) {
			scoreCalculator.setRangeEnd(endDatePicker.getValue());
		}
		
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
