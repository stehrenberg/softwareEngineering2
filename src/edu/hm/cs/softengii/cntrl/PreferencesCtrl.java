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
    @FXML private TableColumn<SupplierClassificationThresholdEntity, SupplierClass> supplierClassCol;

    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;
    @FXML private SeparatorMenuItem userMenuSeperator;

    @FXML private Menu preferencesMenu;
    @FXML private SeparatorMenuItem preferencesMenuSeperator;

    @FXML private Button updateButton;
    @FXML private Button resetButton;
    @FXML private Text errorMessage;
    private String currentError;

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
        alert.setTitle("Update Classification Data");

        alert.setHeaderText("Update Classification Data.");

        alert.setContentText("Do you want to update the supplier classification data?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            DatabaseDataStorage.getInstance().setSupplierClassificationThresholds(scoreData);

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
            DatabaseDataStorage.getInstance().setSupplierClassificationThresholds(scoreData);

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
        //Do nothing, we are already here
    }

    @FXML
    public void gotoDeliveryPreferences() {
        MenuHelper.getInstance().gotoDeliveryPreferences();
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

        Callback<TableColumn<SupplierClassificationThresholdEntity, SupplierClass>,
                TableCell<SupplierClassificationThresholdEntity, SupplierClass>> supplierClassCellFactory =
            new Callback<TableColumn<SupplierClassificationThresholdEntity, SupplierClass>, TableCell<SupplierClassificationThresholdEntity, SupplierClass>>() {

                @Override
                public TableCell<SupplierClassificationThresholdEntity, SupplierClass> call(TableColumn<SupplierClassificationThresholdEntity, SupplierClass> p) {
                    return new EditingStringCell();
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
            new PropertyValueFactory<SupplierClassificationThresholdEntity, SupplierClass>("className")
        );
        supplierClassCol.setCellFactory(supplierClassCellFactory);


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

        supplierClassCol.setOnEditCommit(new EventHandler<TableColumn
                .CellEditEvent<SupplierClassificationThresholdEntity, SupplierClass>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SupplierClassificationThresholdEntity, SupplierClass> t) {
                ((SupplierClassificationThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow())).
                setClassName(t.getNewValue());
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

        public boolean isValidEntry() {

            boolean isValidEntry = true;

            int columnIndex = getTableView().getColumns().indexOf(getTableColumn());
            int rowIndex = getIndex();
            int oldEntry = (int) getItem();
            int newEntry = Integer.parseInt(textField.getText());

            int deliveriesMin = getTableView().getItems().get(getIndex()).getDeliveriesMin();
            int deliveriesMax = getTableView().getItems().get(getIndex()).getDeliveriesMax();
            SupplierClass suppliersClass = getTableView().getItems().get(getIndex()).getClassName();

            switch (columnIndex) {
                case 0:

                    if (newEntry > deliveriesMax) {

                        currentError = "Error: Min cannot be greater than Max in this row.";
                        isValidEntry = false;

                    } else {

                        if (Math.abs(newEntry) >= rowIndex + 1) {



                            int tmpIndex = 0;
                            if(rowIndex > 0) {
                                tmpIndex = --rowIndex;
                            }
                            int currentDeliveriesMin = newEntry;
                            int prevDeliveriesMax = getTableView().getItems().get(tmpIndex).getDeliveriesMax();

                            while (tmpIndex >= 0) {

                                if (currentDeliveriesMin <= prevDeliveriesMax
                                        || currentDeliveriesMin - 1 > prevDeliveriesMax) {

                                    prevDeliveriesMax = currentDeliveriesMin - 1;
                                    getTableView().getItems().get(tmpIndex).setDeliveriesMax(prevDeliveriesMax);

                                    if (prevDeliveriesMax < getTableView().getItems().get(tmpIndex).getDeliveriesMin()) {
                                        currentDeliveriesMin = prevDeliveriesMax;
                                        getTableView().getItems().get(tmpIndex).setDeliveriesMin(currentDeliveriesMin);
                                    }
                                }

                                if (tmpIndex > 0) {
                                    currentDeliveriesMin = getTableView().getItems().get(tmpIndex).getDeliveriesMin();
                                    prevDeliveriesMax = getTableView().getItems().get(tmpIndex - 1).getDeliveriesMax();
                                }

                                tmpIndex--;
                            }

                            populateScoreTable();

                        } else {

                            currentError = "Error: Minimum for DeliveriesMin in this row is: " + (rowIndex + 1);
                            isValidEntry = false;
                        }

                    }
                    break;
                case 1:
                    if (newEntry < deliveriesMin) {
                        currentError = "Error: Max cannot be less than Min in this row.";
                        isValidEntry = false;
                    } else {

                        int tmpIndex = ++rowIndex;
                        int currentDeliveriesMax = newEntry;
                        int nextDeliveriesMin = getTableView().getItems().get(tmpIndex).getDeliveriesMin();

                        while (tmpIndex <= 2) {

                            if (currentDeliveriesMax >= nextDeliveriesMin || currentDeliveriesMax + 1 < nextDeliveriesMin) {

                                nextDeliveriesMin = currentDeliveriesMax + 1;
                                getTableView().getItems().get(tmpIndex).setDeliveriesMin(nextDeliveriesMin);

                                if (nextDeliveriesMin > getTableView().getItems().get(tmpIndex).getDeliveriesMax()) {
                                    currentDeliveriesMax = nextDeliveriesMin;
                                    getTableView().getItems().get(tmpIndex).setDeliveriesMax(currentDeliveriesMax);
                                }
                            }

                            if (tmpIndex < 2) {
                                currentDeliveriesMax = getTableView().getItems().get(tmpIndex).getDeliveriesMax();
                                nextDeliveriesMin = getTableView().getItems().get(tmpIndex + 1).getDeliveriesMin();
                            }

                            tmpIndex++;
                        }

                        populateScoreTable();
                    }
                    break;
            }

            return isValidEntry;
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER && isValidEntry()) {
                        errorMessage.setText("");
                        commitEdit(Integer.parseInt(textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        textField.setText(getString());
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB && isValidEntry()) {
                        errorMessage.setText("");
                        commitEdit(Integer.parseInt(textField.getText()));
                        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                        if (nextColumn != null) {
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                        }
                    } else if ((t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.TAB) && !isValidEntry()) {
                        errorMessage.setText("");
                        errorMessage.setText(currentError);
                        textField.setText(getString());
                        cancelEdit();
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
    /**
     * A editable Cell in a TableView.
     * @author Apachen Pub Team
     *
     */
    class EditingStringCell extends TableCell<SupplierClassificationThresholdEntity, SupplierClass> {

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
        public void updateItem(SupplierClass item, boolean empty) {
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

        public boolean isValidEntry() {

            int columnIndex = getTableView().getColumns().indexOf(getTableColumn());
            int rowIndex = getIndex();
            String newEntry = textField.getText();

            boolean isValidEntry = newEntry != null && !newEntry.isEmpty()
                    && ((newEntry.equals("ONE_OFF"))
                        || (newEntry.equals("NORMAL"))
                        || (newEntry.equals("TOP")));

            if(!isValidEntry) {
                currentError = "Error: Please use 'ONE_OFF', 'NORMAL' or 'TOP'.";
            }

            return isValidEntry;
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER && isValidEntry()) {
                        errorMessage.setText("");
                        commitEdit(SupplierClass.valueOf(textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        textField.setText(getString());
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB && isValidEntry()) {
                        errorMessage.setText("");
                        commitEdit(SupplierClass.valueOf(textField.getText()));
                        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                        if (nextColumn != null) {
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                        }
                    } else if ((t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.TAB) && !isValidEntry()) {
                        errorMessage.setText("");
                        errorMessage.setText(currentError);
                        textField.setText(getString());
                        cancelEdit();
                    }
                }
            });


            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue && textField != null) {
                        commitEdit(SupplierClass.valueOf(textField.getText()));
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
