import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.db.userAuth.DatabaseUserAuth;
import edu.hm.cs.softengii.utils.SettingsPropertiesHelper;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class to represent the main class of this program
 * @author Simon
 */
public class SupplyAlyticsApp extends Application {

	/**
	 * Current JavaFX stage
	 */
    private Stage stage;
    
    /**
     * Singleton instance of this class
     */
    private static SupplyAlyticsApp instance;

    /**
     * Create a new instance 
     */
    public SupplyAlyticsApp() {
        instance = this;
    }

    /**
     * Get singleton instance of this class
     * @return
     */
    public static SupplyAlyticsApp getInstance() {
        return instance;
    }
    /**
     * Main methods to start the pogram
     * @param args
     */
    public static void main(String[] args) {
    	
    	launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle(SettingsPropertiesHelper.getInstance().getAppTitle());
        MenuHelper.getInstance().setStage(primaryStage);
        Database.createInstance();

        if(DatabaseUserAuth.getInstance().isEmpty()) {
            MenuHelper.getInstance().gotoRegistration();
        } else {
            MenuHelper.getInstance().gotoLogin();
        }

        primaryStage.show();
    }
}
