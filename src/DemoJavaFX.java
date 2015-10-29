
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DemoJavaFX extends Application {

	private final BorderPane border = new BorderPane();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Test");

		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().addAll(new Separator(Orientation.VERTICAL), createSettingsPane());
		
		border.setTop(createMenuBar());
		border.setCenter(createCompareChart());
		border.setRight(hbox);

		Scene scene = new Scene(border, 600, 400);
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public MenuBar createMenuBar() {
		MenuBar menuBar = new MenuBar();

		Menu fileMenu = new Menu("File");
		Menu perspMenu = new Menu("Perspectives");
		Menu helpMenu = new Menu("Help");

		MenuItem export = new MenuItem("Export");
		export.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("Export");
			}
		});
		MenuItem comp = new MenuItem("Compare Suppliers");
		comp.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("Compare Suppliers");
			}
		});
		MenuItem average = new MenuItem("Supplier average");
		average.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("Supplier average");
			}
		});
		MenuItem version = new MenuItem("Version");
		version.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("Version");
			}
		});
		MenuItem contact = new MenuItem("Contact");
		contact.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("Contact");
			}
		});


		fileMenu.getItems().addAll(export);
		perspMenu.getItems().addAll(comp, average);
		helpMenu.getItems().addAll(version, contact);
		
		menuBar.getMenus().addAll(fileMenu, perspMenu, helpMenu);

		return menuBar;
	}

	public BarChart<String, Number> createCompareChart() {

		final BarChart<String, Number> bc = new BarChart<String, Number>(new CategoryAxis(), new NumberAxis());

		XYChart.Series<String, Number> supplier1 = new XYChart.Series<>();
		supplier1.setName("Supplier 1");
		supplier1.getData().add(new XYChart.Data<String, Number>("very early", 20));
		supplier1.getData().add(new XYChart.Data<String, Number>("early", 40));
		supplier1.getData().add(new XYChart.Data<String, Number>("in time", 90));
		supplier1.getData().add(new XYChart.Data<String, Number>("late", 10));
		supplier1.getData().add(new XYChart.Data<String, Number>("very late", 5));

		XYChart.Series<String, Number> supplier2 = new XYChart.Series<>();
		supplier2.setName("Supplier 2");
		supplier2.getData().add(new XYChart.Data<String, Number>("very early", 20));
		supplier2.getData().add(new XYChart.Data<String, Number>("early", 30));
		supplier2.getData().add(new XYChart.Data<String, Number>("in time", 80));
		supplier2.getData().add(new XYChart.Data<String, Number>("late", 20));
		supplier2.getData().add(new XYChart.Data<String, Number>("very late", 10));

		XYChart.Series<String, Number> supplier3 = new XYChart.Series<>();
		supplier3.setName("Supplier 3");
		supplier3.getData().add(new XYChart.Data<String, Number>("very early", 20));
		supplier3.getData().add(new XYChart.Data<String, Number>("early", 30));
		supplier3.getData().add(new XYChart.Data<String, Number>("in time", 80));
		supplier3.getData().add(new XYChart.Data<String, Number>("late", 20));
		supplier3.getData().add(new XYChart.Data<String, Number>("very late", 10));

		bc.getData().addAll(supplier1, supplier2, supplier3);
		bc.setLegendVisible(false);
		
		
		return bc;
	}

	public GridPane createSettingsPane() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		// grid.setGridLinesVisible(true);

		
		ColumnConstraints cc = new ColumnConstraints();
		cc.setHalignment(HPos.CENTER);
		grid.getColumnConstraints().add(cc);
		
		Text dataRangeText = new Text("Data Range");
		grid.add(dataRangeText, 0, 0);

		Text startDateText = new Text("Start date");
		grid.add(startDateText, 0, 1);

		DatePicker startPicker = new DatePicker();
		startPicker.setEditable(false);
		grid.add(startPicker, 0, 2);
		
		Text endDateText = new Text("End date");
		grid.add(endDateText, 0, 3);

		DatePicker endPicker = new DatePicker();
		endPicker.setEditable(false);
		grid.add(endPicker, 0, 4);

		grid.add(new Separator(), 0, 5);
		
		Text selModesText = new Text("Selection Modes");
		grid.add(selModesText, 0, 6);

		IDatabase database = new Database();
        database.establishConnection();
        ObservableList<String> options = FXCollections.observableArrayList(database.getSuppliers());
        database.closeConnection();
		
		ComboBox<String> comboBox1 = new ComboBox<>(options);
		ComboBox<String> comboBox2 = new ComboBox<>(options);
		
		comboBox1.setMaxWidth(200);
		comboBox2.setMaxWidth(200);
		
		grid.add(comboBox1, 0, 7);
		grid.add(comboBox2, 0, 8);
		
		Button add = new Button("+");
		Button remove = new Button("-");

		HBox hBox = new HBox(20);
		hBox.getChildren().addAll(add, remove);
		
		grid.add(hBox, 0, 9);

		return grid;
	}
}
