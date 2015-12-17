package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.dataStorage.DatabaseDataStorage;
import edu.hm.cs.softengii.db.dataStorage.DeliveryRange;
import edu.hm.cs.softengii.db.dataStorage.DeliveryRangeThresholdEntity;
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
public class DeliveryPreferencesCtrl implements Initializable {

    private final ObservableList<DeliveryRangeThresholdEntity> scoreData = FXCollections.observableArrayList();

    @FXML private AnchorPane rootPane;

    @FXML private TableView<DeliveryRangeThresholdEntity> deliveryRangeTable;
    @FXML private TableColumn<DeliveryRangeThresholdEntity, Number> daysMinCol;
    @FXML private TableColumn<DeliveryRangeThresholdEntity, Number> daysMaxCol;
    @FXML private TableColumn<DeliveryRangeThresholdEntity, DeliveryRange> deliveryRangeCol;

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

        scoreData.addAll(DatabaseDataStorage.getInstance().getDeliveryRangeThresholds());
        populateScoreTable();
    }

    @FXML
    void updateScoreSetttings(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Classification Data");

        alert.setHeaderText("Update Classification Data.");

        alert.setContentText("Do you want to update the supplier classification data?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            DatabaseDataStorage.getInstance().setDeliveryRangeThresholds(scoreData);

            errorMessage.setFill(Color.GREEN);
            errorMessage.setText("Supplier classification data updated.");
        }
    }

    @FXML
    void resetScoreSetttings(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reset Classification Data");

        alert.setHeaderText("Reset Classification Data.");

        alert.setContentText("Do you want to reset the supplier classification data?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            populateScoreTableWithDefaults();
            DatabaseDataStorage.getInstance().setDeliveryRangeThresholds(scoreData);

            errorMessage.setFill(Color.GREEN);
            errorMessage.setText("Classification data reset to defaults.");
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
        MenuHelper.getInstance().gotoPreferences();
    }

    @FXML
    public void gotoDeliveryPreferences() {
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
            preferencesMenuSeperator.setVisible(true);
        } else {
            newUserMenuItem.setVisible(false);
            manageAllUsersMenuItem.setVisible(false);
            userMenuSeperator.setVisible(false);
            preferencesMenu.setVisible(false);
            preferencesMenuSeperator.setVisible(false);
        }
    }

    private void populateScoreTableWithDefaults() {
        scoreData.removeAll();
        scoreData.clear();
        scoreData.addAll(DatabaseDataStorage.getInstance().getDeliveryRangeDefaults());
        populateScoreTable();
    }

    private void populateScoreTable() {

        deliveryRangeTable.setEditable(true);

        Callback<TableColumn<DeliveryRangeThresholdEntity, Number>, TableCell<DeliveryRangeThresholdEntity, Number>> cellFactory =
            new Callback<TableColumn<DeliveryRangeThresholdEntity, Number>, TableCell<DeliveryRangeThresholdEntity, Number>>() {

                @Override
                public TableCell<DeliveryRangeThresholdEntity, Number> call(TableColumn<DeliveryRangeThresholdEntity, Number> p) {
                    return new EditingCell();
                }
        };

        Callback<TableColumn<DeliveryRangeThresholdEntity, DeliveryRange>,
                TableCell<DeliveryRangeThresholdEntity, DeliveryRange>> deliveryRangeCellFactory =
            new Callback<TableColumn<DeliveryRangeThresholdEntity, DeliveryRange>, TableCell<DeliveryRangeThresholdEntity, DeliveryRange>>() {

                @Override
                public TableCell<DeliveryRangeThresholdEntity, DeliveryRange> call(TableColumn<DeliveryRangeThresholdEntity, DeliveryRange> p) {
                    return new EditingStringCell();
                }
        };

        daysMinCol.setCellValueFactory(
            new PropertyValueFactory<DeliveryRangeThresholdEntity, Number>("daysMin")
        );
        daysMinCol.setCellFactory(cellFactory);

        daysMaxCol.setCellValueFactory(
            new PropertyValueFactory<DeliveryRangeThresholdEntity, Number>("daysMax")
        );
        daysMaxCol.setCellFactory(cellFactory);

        deliveryRangeCol.setCellValueFactory(
            new PropertyValueFactory<DeliveryRangeThresholdEntity, DeliveryRange>("deliveryRangeName")
        );
        deliveryRangeCol.setCellFactory(deliveryRangeCellFactory);


        deliveryRangeTable.setItems(scoreData);


        daysMinCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DeliveryRangeThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DeliveryRangeThresholdEntity, Number> t) {
                ((DeliveryRangeThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setDaysMin((int) t.getNewValue());
            }
        });

        daysMaxCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DeliveryRangeThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DeliveryRangeThresholdEntity, Number> t) {
                ((DeliveryRangeThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setDaysMax((int)t.getNewValue());
            }
        });

        deliveryRangeCol.setOnEditCommit(new EventHandler<TableColumn
                .CellEditEvent<DeliveryRangeThresholdEntity, DeliveryRange>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DeliveryRangeThresholdEntity, DeliveryRange> t) {
                ((DeliveryRangeThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setDeliveryRangeName(t.getNewValue());
            }
        });
    }
    
    /**
     * A editable Cell in a TableView.
     * @author Apachen Pub Team
     *
     */
    class EditingCell extends TableCell<DeliveryRangeThresholdEntity, Number> {

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
        private TableColumn<DeliveryRangeThresholdEntity, ?> getNextColumn(boolean forward) {
            List<TableColumn<DeliveryRangeThresholdEntity, ?>> columns = new ArrayList<>();
            for (TableColumn<DeliveryRangeThresholdEntity, ?> column : getTableView().getColumns()) {
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

        private List<TableColumn<DeliveryRangeThresholdEntity, ?>> getLeaves(TableColumn<DeliveryRangeThresholdEntity, ?> root) {
            List<TableColumn<DeliveryRangeThresholdEntity, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                //We only want the leaves that are editable.
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<DeliveryRangeThresholdEntity, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }
    }
    /**
     * A editable Cell in a TableView.
     * @author Apachen Pub Team
     *
     */
    class EditingStringCell extends TableCell<DeliveryRangeThresholdEntity, DeliveryRange> {

        private TextField textField;

        public EditingStringCell() {
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
        public void updateItem(DeliveryRange item, boolean empty) {
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
                        commitEdit(DeliveryRange.valueOf(textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB) {
                        commitEdit(DeliveryRange.valueOf(textField.getText()));
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
                        commitEdit(DeliveryRange.valueOf(textField.getText()));
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
        private TableColumn<DeliveryRangeThresholdEntity, ?> getNextColumn(boolean forward) {
            List<TableColumn<DeliveryRangeThresholdEntity, ?>> columns = new ArrayList<>();
            for (TableColumn<DeliveryRangeThresholdEntity, ?> column : getTableView().getColumns()) {
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

        private List<TableColumn<DeliveryRangeThresholdEntity, ?>> getLeaves(TableColumn<DeliveryRangeThresholdEntity, ?> root) {
            List<TableColumn<DeliveryRangeThresholdEntity, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                //We only want the leaves that are editable.
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<DeliveryRangeThresholdEntity, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }
    }
}
