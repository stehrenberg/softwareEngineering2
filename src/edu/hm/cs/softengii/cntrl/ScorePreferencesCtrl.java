package edu.hm.cs.softengii.cntrl;

import com.sun.org.apache.xpath.internal.SourceTree;
import edu.hm.cs.softengii.db.dataStorage.DatabaseDataStorage;
import edu.hm.cs.softengii.db.dataStorage.ScoreThresholdEntity;
import edu.hm.cs.softengii.utils.MenuHelper;
import edu.hm.cs.softengii.utils.Session;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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

import javax.naming.Binding;
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
public class ScorePreferencesCtrl implements Initializable {

    private final ObservableList<ScoreThresholdEntity> scoreData = FXCollections.observableArrayList();

    @FXML private AnchorPane rootPane;

    @FXML private TableView<ScoreThresholdEntity> scoreTable;
    @FXML private TableColumn<ScoreThresholdEntity, Number> earlyMinCol;
    @FXML private TableColumn<ScoreThresholdEntity, Number> earlyMaxCol;
    @FXML private TableColumn<ScoreThresholdEntity, Number> lateMinCol;
    @FXML private TableColumn<ScoreThresholdEntity, Number> lateMaxCol;
    @FXML private TableColumn<ScoreThresholdEntity, Number> scoreCol;

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

        scoreData.addAll(DatabaseDataStorage.getInstance().getScoreThresholds());
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

            DatabaseDataStorage.getInstance().setScoreThresholds(scoreData);

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
            DatabaseDataStorage.getInstance().setScoreThresholds(scoreData);

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
    public void gotoDeliveryPreferences() {
        MenuHelper.getInstance().gotoDeliveryPreferences();
    }

    @FXML
    public void gotoPreferences() {
        MenuHelper.getInstance().gotoPreferences();
    }


    @FXML
    public void gotoScorePreferences() {
        // Do nothing, we are already here
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
        scoreData.addAll(DatabaseDataStorage.getInstance().getScoreDefaults());
        populateScoreTable();
    }

    private void populateScoreTable() {

        scoreTable.setEditable(true);

        Callback<TableColumn<ScoreThresholdEntity, Number>, TableCell<ScoreThresholdEntity, Number>> cellFactory =
            new Callback<TableColumn<ScoreThresholdEntity, Number>, TableCell<ScoreThresholdEntity, Number>>() {

                @Override
                public TableCell<ScoreThresholdEntity, Number> call(TableColumn<ScoreThresholdEntity, Number> p) {
                    return new EditingCell();
                }
        };

        earlyMinCol.setCellValueFactory(
            new PropertyValueFactory<ScoreThresholdEntity, Number>("earlyMin")
        );
        earlyMinCol.setCellFactory(cellFactory);

        earlyMaxCol.setCellValueFactory(
            new PropertyValueFactory<ScoreThresholdEntity, Number>("earlyMax")
        );
        earlyMaxCol.setCellFactory(cellFactory);

        lateMinCol.setCellValueFactory(
            new PropertyValueFactory<ScoreThresholdEntity, Number>("lateMin")
        );
        lateMinCol.setCellFactory(cellFactory);

        lateMaxCol.setCellValueFactory(
            new PropertyValueFactory<ScoreThresholdEntity, Number>("lateMax")
        );
        lateMaxCol.setCellFactory(cellFactory);

        scoreCol.setCellValueFactory(
            new PropertyValueFactory<ScoreThresholdEntity, Number>("scoreValue")
        );
        scoreCol.setCellFactory(cellFactory);

        scoreTable.setItems(scoreData);


        earlyMinCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ScoreThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ScoreThresholdEntity, Number> t) {
                ((ScoreThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setEarlyMin((int) t.getNewValue());
            }
        });

        earlyMaxCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ScoreThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ScoreThresholdEntity, Number> t) {
                ((ScoreThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setEarlyMax((int) t.getNewValue());
            }
        });

        lateMinCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ScoreThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ScoreThresholdEntity, Number> t) {
                ((ScoreThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setLateMin((int) t.getNewValue());
            }
        });

        lateMaxCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ScoreThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ScoreThresholdEntity, Number> t) {
                ((ScoreThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setLateMax((int) t.getNewValue());
            }
        });

        scoreCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ScoreThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ScoreThresholdEntity, Number> t) {
                ((ScoreThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                        .setScoreValue((int) t.getNewValue());
            }
        });


    }
    
    /**
     * A editable Cell in a TableView.
     * @author Apachen Pub Team
     *
     */
    class EditingCell extends TableCell<ScoreThresholdEntity, Number> {

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


        public boolean isValidEntry(){

            boolean isValidEntry = true;

            int columnIndex = getTableView().getColumns().indexOf(getTableColumn());
            int rowIndex = getIndex();
            int oldEntry = (int)getItem();
            int newEntry = Integer.parseInt(textField.getText());

            int earlyMin = getTableView().getItems().get(getIndex()).getEarlyMin();
            int earlyMax = getTableView().getItems().get(getIndex()).getEarlyMax();
            int lateMin = getTableView().getItems().get(getIndex()).getLateMin();
            int lateMax = getTableView().getItems().get(getIndex()).getLateMax();

            switch (columnIndex) {
                case 0:

                    if (newEntry > earlyMax) {

                        currentError = "Error: EarlyMin cannot be greater than EarlyMax in this row.";
                        isValidEntry = false;

                    } else {

                        int tmpIndex = ++rowIndex;
                        int currentEarlyMin = newEntry;
                        int nextEarlyMax = getTableView().getItems().get(tmpIndex).getEarlyMax();

                        while (tmpIndex <= 5) {

                            if (currentEarlyMin <= nextEarlyMax || currentEarlyMin - 1 > nextEarlyMax) {

                                nextEarlyMax = currentEarlyMin - 1;
                                getTableView().getItems().get(tmpIndex).setEarlyMax(nextEarlyMax);

                                if (nextEarlyMax < getTableView().getItems().get(tmpIndex).getEarlyMin()) {
                                    currentEarlyMin = nextEarlyMax;
                                    getTableView().getItems().get(tmpIndex).setEarlyMin(currentEarlyMin);
                                }
                            }

                            if(tmpIndex < 5) {
                                currentEarlyMin = getTableView().getItems().get(tmpIndex).getEarlyMin();
                                nextEarlyMax = getTableView().getItems().get(tmpIndex + 1).getEarlyMax();
                            }

                            tmpIndex++;
                        }

                        populateScoreTable();
                    }
                    break;

                case 1:

                    if (newEntry < earlyMin) {

                        currentError = "Error: EarlyMax cannot be less than EarlyMin in this row.";
                        isValidEntry = false;

                    } else {

                        if(Math.abs(newEntry) >= rowIndex + 1) {

                            int tmpIndex = --rowIndex;
                            int currentEarlyMax = newEntry;
                            int prevEarlyMin = getTableView().getItems().get(tmpIndex).getEarlyMin();

                            while (tmpIndex >= 0) {

                                if (currentEarlyMax >= prevEarlyMin || currentEarlyMax + 1 < prevEarlyMin) {

                                    prevEarlyMin = currentEarlyMax + 1;
                                    getTableView().getItems().get(tmpIndex).setEarlyMin(prevEarlyMin);

                                    if (prevEarlyMin > getTableView().getItems().get(tmpIndex).getEarlyMax()) {
                                        currentEarlyMax = prevEarlyMin;
                                        getTableView().getItems().get(tmpIndex).setEarlyMax(currentEarlyMax);
                                    }
                                }

                                if (tmpIndex > 0) {
                                    currentEarlyMax = getTableView().getItems().get(tmpIndex).getEarlyMax();
                                    prevEarlyMin = getTableView().getItems().get(tmpIndex + 1).getEarlyMin();
                                }

                                tmpIndex--;
                            }

                            populateScoreTable();

                        } else {

                            currentError = "Error: Maximum for EarlyMax in this row is: " + ((rowIndex + 1) * -1);
                            isValidEntry = false;
                        }
                    }
                    break;

                case 2:

                    if (newEntry > lateMax) {

                        currentError = "Error: LateMin cannot be greater than LateMax in this row.";
                        isValidEntry = false;

                    } else {

                        if (Math.abs(newEntry) >= rowIndex + 1) {

                            int tmpIndex = --rowIndex;
                            int currentLateMin = newEntry;
                            int prevLateMax = getTableView().getItems().get(tmpIndex).getLateMax();

                            while (tmpIndex >= 0) {

                                if (currentLateMin <= prevLateMax || currentLateMin - 1 > prevLateMax) {

                                    prevLateMax = currentLateMin - 1;
                                    getTableView().getItems().get(tmpIndex).setLateMax(prevLateMax);

                                    if (prevLateMax < getTableView().getItems().get(tmpIndex).getLateMin()) {
                                        currentLateMin = prevLateMax;
                                        getTableView().getItems().get(tmpIndex).setLateMin(currentLateMin);
                                    }
                                }

                                if (tmpIndex > 0) {
                                    currentLateMin = getTableView().getItems().get(tmpIndex).getLateMin();
                                    prevLateMax = getTableView().getItems().get(tmpIndex - 1).getLateMax();
                                }

                                tmpIndex--;
                            }

                            populateScoreTable();

                        } else {

                            currentError = "Error: Minimum for LateMin in this row is: " + (rowIndex + 1);
                            isValidEntry = false;
                        }

                    }
                    break;

                case 3:

                    if (newEntry < lateMin) {
                        currentError = "Error: LateMax cannot be less than LateMin in this row.";
                        isValidEntry = false;
                    } else {

                        int tmpIndex = ++rowIndex;
                        int currentLateMax = newEntry;
                        int nextLateMin = getTableView().getItems().get(tmpIndex).getLateMin();

                        while (tmpIndex <= 5) {

                            if (currentLateMax >= nextLateMin || currentLateMax + 1 < nextLateMin) {

                                nextLateMin = currentLateMax + 1;
                                getTableView().getItems().get(tmpIndex).setLateMin(nextLateMin);

                                if (nextLateMin > getTableView().getItems().get(tmpIndex).getLateMax()) {
                                    currentLateMax = nextLateMin;
                                    getTableView().getItems().get(tmpIndex).setLateMax(currentLateMax);
                                }
                            }

                            if (tmpIndex < 5) {
                                currentLateMax = getTableView().getItems().get(tmpIndex).getLateMax();
                                nextLateMin = getTableView().getItems().get(tmpIndex + 1).getLateMin();
                            }

                            tmpIndex++;
                        }

                        populateScoreTable();
                    }
                    break;
                case 4:
                    if(newEntry > 100) {
                        currentError = "Error: The maximum for the score is 100%";
                        isValidEntry = false;
                    } else if (newEntry < 1) {
                        currentError = "Error: The minimum for the score is 1%";
                        isValidEntry = false;
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
                    if (!newValue && textField != null && isValidEntry()) {
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
        private TableColumn<ScoreThresholdEntity, ?> getNextColumn(boolean forward) {
            List<TableColumn<ScoreThresholdEntity, ?>> columns = new ArrayList<>();
            for (TableColumn<ScoreThresholdEntity, ?> column : getTableView().getColumns()) {
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

        private List<TableColumn<ScoreThresholdEntity, ?>> getLeaves(TableColumn<ScoreThresholdEntity, ?> root) {
            List<TableColumn<ScoreThresholdEntity, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                //We only want the leaves that are editable.
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<ScoreThresholdEntity, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }
    }
}
