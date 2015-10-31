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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.hm.cs.softengii.control.ScoreCalculator;
import edu.hm.cs.softengii.model.Delivery;
import edu.hm.cs.softengii.model.Supplier;

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
    	
		ArrayList<Delivery> deliveries = new ArrayList<>();
		deliveries.add(new Delivery(new Date(2015, 10, 16), new Date(2015, 10, 30)));
		deliveries.add(new Delivery(new Date(2014, 7, 2), new Date(2014, 7, 3)));
		deliveries.add(new Delivery(new Date(2014, 2, 15), new Date(2014, 2, 11)));
		deliveries.add(new Delivery(new Date(2013, 11, 19), new Date(2013, 11, 23)));
		Supplier supplier = new Supplier("MySupplier", deliveries);
		
		double score = scoreCalculator.calculateScore(supplier);
        
		list.add(new SupplierScoreViewModel(supplier.getName(), score));
		
        TableView<SupplierScoreViewModel> tableView = new TableView<>();
        
        TableColumn supplierColumn = new TableColumn("Supplier");
        supplierColumn.setMinWidth(100);
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        
        TableColumn scoreColumn = new TableColumn("Score");
        scoreColumn.setMinWidth(100);
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

        public SupplierScoreViewModel(String supplierName, double score) {
        	
            this.supplierName = new SimpleStringProperty(supplierName);
            this.score = new SimpleStringProperty(String.valueOf(Math.round(score)));
        }
        
        public String getSupplierName() {
        	return supplierName.get();
        }
        
        public String getScore() {
            return score.get();
        }
        
        public void setScore(double score) {
        	this.score.set(String.valueOf(Math.round(score)));
        }
        
        public void setSupplierName(String supplierName) {
        	this.supplierName.set(supplierName);
        }
    }
}