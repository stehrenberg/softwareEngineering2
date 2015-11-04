package edu.hm.cs.softengii;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.hm.cs.softengii.control.ScoreCalculator;
import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.db.sap.Delivery;
import edu.hm.cs.softengii.db.sap.IDatabase;
import edu.hm.cs.softengii.db.sap.Supplier;

public class SupplyAlyticsApp extends Application {

    private Stage stage;
    private static SupplyAlyticsApp instance;

    public SupplyAlyticsApp() {
        instance = this;
    }

    public static SupplyAlyticsApp getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    	stage = primaryStage;
    	
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
		
        TableView<SupplierScoreViewModel> tableView = new TableView<>();
        
        TableColumn supplierColumn = new TableColumn("Supplier");
        supplierColumn.setMinWidth(300);
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        
        TableColumn scoreColumn = new TableColumn("Score");
        scoreColumn.setMinWidth(50);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        
        tableView.getColumns().addAll(supplierColumn, scoreColumn);
        
        tableView.setItems(list);
        
		stage.setTitle("Suppliers score (" + list.size() + ")");
        stage.setScene(new Scene(tableView, 640, 480));
        stage.show();
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