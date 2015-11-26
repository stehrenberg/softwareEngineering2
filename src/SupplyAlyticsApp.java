import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.db.sap.Database;
import edu.hm.cs.softengii.db.userAuth.DatabaseUserAuth;
import edu.hm.cs.softengii.utils.SettingsPropertiesHelper;
import javafx.application.Application;
import javafx.stage.Stage;

public class SupplyAlyticsApp extends Application {

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
