package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.dataStorage.DatabaseDataStorage;
import edu.hm.cs.softengii.db.dataStorage.SupplierClass;
import edu.hm.cs.softengii.db.dataStorage.SupplierClassificationThresholdEntity;
import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.utils.Session;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * JavaFX Controller for 'preferences.fxml'.
 * It handles the actions triggered in this view.
 * 
 * @author Apachen Pub Team
 *
 */
public class PreferencesCtrl implements Initializable {

    private final ObservableList<SupplierClassificationThresholdEntity> scoreData = FXCollections.observableArrayList();

    @FXML private AnchorPane rootPane;

    @FXML private TableView<SupplierClassificationThresholdEntity> supplierClassTable;
    @FXML private TableColumn<SupplierClassificationThresholdEntity, Number> deliveriesMinCol;
    @FXML private TableColumn<SupplierClassificationThresholdEntity, Number> deliveriesMaxCol;
    @FXML private TableColumn<SupplierClassificationThresholdEntity, Number> supplierClassCol;

    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;
    @FXML private SeparatorMenuItem userMenuSeperator;

    @FXML private Menu preferencesMenu;
    @FXML private SeparatorMenuItem preferencesMenuSeperator;

    @FXML private Button updateButton;
    @FXML private Button resetButton;
    @FXML private Text errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        setAdminMenusVisible(Session.getInstance().getAuthenticatedUser().isAdmin());

        scoreData.addAll(DatabaseDataStorage.getInstance().getSupplierClassificationThresholds());
        populateScoreTable();
    }

    @FXML
    void updateScoreSetttings(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Score Data");

        alert.setHeaderText("Update Score Data.");

        alert.setContentText("Do you want to update the score data?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            DatabaseDataStorage.getInstance().setSupplierClassificationThresholds(scoreData);

            errorMessage.setFill(Color.GREEN);
            errorMessage.setText("Score data updated.");
        }
    }

    @FXML
    void resetScoreSetttings(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reset Score Data");

        alert.setHeaderText("Reset Score Data.");

        alert.setContentText("Do you want to reset the score data?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            populateScoreTableWithDefaults();
            DatabaseDataStorage.getInstance().setSupplierClassificationThresholds(scoreData);

            errorMessage.setFill(Color.GREEN);
            errorMessage.setText("Score data reset to defaults.");
        }
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
        //Do nothing, we are already here
    }

    @FXML
    public void gotoScorePreferences() {
        MenuHelper.getInstance().gotoScorePreferences();
    }

    @FXML
    public void quitApplication() {
        MenuHelper.getInstance().quitApplication();
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

    private void populateScoreTableWithDefaults() {
        scoreData.removeAll();
        scoreData.clear();
        scoreData.addAll(DatabaseDataStorage.getInstance().getSupplierClassificationDefaults());
        populateScoreTable();
    }

    private void populateScoreTable() {

        supplierClassTable.setEditable(true);

        Callback<TableColumn<SupplierClassificationThresholdEntity, Number>, TableCell<SupplierClassificationThresholdEntity, Number>> cellFactory =
            new Callback<TableColumn<SupplierClassificationThresholdEntity, Number>, TableCell<SupplierClassificationThresholdEntity, Number>>() {

                @Override
                public TableCell<SupplierClassificationThresholdEntity, Number> call(TableColumn<SupplierClassificationThresholdEntity, Number> p) {
                    return new EditingCell();
                }
        };

        deliveriesMinCol.setCellValueFactory(
            new PropertyValueFactory<SupplierClassificationThresholdEntity, Number>("deliveriesMin")
        );
        deliveriesMinCol.setCellFactory(cellFactory);

        deliveriesMaxCol.setCellValueFactory(
            new PropertyValueFactory<SupplierClassificationThresholdEntity, Number>("deliveriesMax")
        );
        deliveriesMaxCol.setCellFactory(cellFactory);

        supplierClassCol.setCellValueFactory(
            new PropertyValueFactory<SupplierClassificationThresholdEntity, Number>("supplierClass")
        );
        supplierClassCol.setCellFactory(cellFactory);


        supplierClassTable.setItems(scoreData);


        deliveriesMinCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SupplierClassificationThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SupplierClassificationThresholdEntity, Number> t) {
                ((SupplierClassificationThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow())).
                    setDeliveriesMin((int) t.getNewValue());
            }
        });

        deliveriesMaxCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SupplierClassificationThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SupplierClassificationThresholdEntity, Number> t) {
                ((SupplierClassificationThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setDeliveriesMax((int)t.getNewValue());
            }
        });

        supplierClassCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SupplierClassificationThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SupplierClassificationThresholdEntity, Number> t) {
                ((SupplierClassificationThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow())).
                setClassificationName(SupplierClass.values()[(int)t.getNewValue()]); ;
            }
        });
    }
    
    /**
     * A editable Cell in a TableView.
     * @author Apachen Pub Team
     *
     */
    class EditingCell extends TableCell<SupplierClassificationThresholdEntity, Number> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textField.requestFocus();
                    textField.selectAll();
                }
            });
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem().toString());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(Number item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(Integer.parseInt(textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB) {
                        commitEdit(Integer.parseInt(textField.getText()));
                        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                        if (nextColumn != null) {
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                        }
                    }
                }
            });

            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue && textField != null) {
                        commitEdit(Integer.parseInt(textField.getText()));
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }

        /**
         * @param forward true gets the column to the right, false the column to the left of the current column
         * @return
         */
        private TableColumn<SupplierClassificationThresholdEntity, ?> getNextColumn(boolean forward) {
            List<TableColumn<SupplierClassificationThresholdEntity, ?>> columns = new ArrayList<>();
            for (TableColumn<SupplierClassificationThresholdEntity, ?> column : getTableView().getColumns()) {
                columns.addAll(getLeaves(column));
            }
            //There is no other column that supports editing.
            if (columns.size() < 2) {
                return null;
            }
            int currentIndex = columns.indexOf(getTableColumn());
            int nextIndex = currentIndex;
            if (forward) {
                nextIndex++;
                if (nextIndex > columns.size() - 1) {
                    nextIndex = 0;
                }
            } else {
                nextIndex--;
                if (nextIndex < 0) {
                    nextIndex = columns.size() - 1;
                }
            }
            return columns.get(nextIndex);
        }

        private List<TableColumn<SupplierClassificationThresholdEntity, ?>> getLeaves(TableColumn<SupplierClassificationThresholdEntity, ?> root) {
            List<TableColumn<SupplierClassificationThresholdEntity, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                //We only want the leaves that are editable.
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<SupplierClassificationThresholdEntity, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }
    }
}
