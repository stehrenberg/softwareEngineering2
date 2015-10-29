import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class DataDisplayer extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
        IDatabase database = new Database();
        database.establishConnection();
        ObservableList<String> list = FXCollections.observableArrayList(database.getSuppliers());
        database.closeConnection();
        
        ListView<String> listView = new ListView<>();
        listView.setItems(list);
        
		stage.setTitle("All supplieres (" + list.size() + ")");
        stage.setScene(new Scene(listView, 640, 480));
        stage.show();
	}
}