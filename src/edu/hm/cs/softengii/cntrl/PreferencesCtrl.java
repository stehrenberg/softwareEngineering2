package edu.hm.cs.softengii.cntrl;

import edu.hm.cs.softengii.db.dataStorage.DatabaseDataStorage;
import edu.hm.cs.softengii.db.dataStorage.ScoreThresholdEntity;
import edu.hm.cs.softengii.utils.Session;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreferencesCtrl implements Initializable {

    private Stage stage;

    @FXML private AnchorPane rootPane;

    @FXML private TableView<ScoreThresholdEntity> scoreTable;
    @FXML private TableColumn earlyMinCol;
    @FXML private TableColumn earlyMaxCol;
    @FXML private TableColumn lateMinCol;
    @FXML private TableColumn lateMaxCol;
    @FXML private TableColumn scoreCol;

    @FXML private MenuItem newUserMenuItem;
    @FXML private MenuItem manageAllUsersMenuItem;
    @FXML private SeparatorMenuItem userMenuSeperator;

    @FXML private MenuItem preferencesMenuItem;
    @FXML private SeparatorMenuItem preferencesMenuSeperator;

    @FXML private Button updateButton;
    @FXML private Button resetButton;
    @FXML private Text errorMessage;

    final ObservableList<ScoreThresholdEntity> scoreData = FXCollections.observableArrayList();

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
    void gotoCreateNewUser(ActionEvent event) {

        try {

            String fxmlPath = "../view/createNewUser.fxml";
            FXMLLoader loader = new FXMLLoader(PreferencesCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((CreateNewUserCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(PreferencesCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoManageAllUsers(ActionEvent event) {

        try {

            String fxmlPath = "../view/manageAllUsers.fxml";
            FXMLLoader loader = new FXMLLoader(PreferencesCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((ManageAllUsersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(PreferencesCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void gotoCompareSuppliers(ActionEvent event) {

        try {

            String fxmlPath = "../view/compareSuppliers.fxml";
            FXMLLoader loader = new FXMLLoader(PreferencesCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((CompareSuppliersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(PreferencesCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoAverageSuppliers(ActionEvent event) {

        try {

            String fxmlPath = "../view/averageSuppliers.fxml";
            FXMLLoader loader = new FXMLLoader(PreferencesCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((AverageSuppliersCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(PreferencesCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void gotoUserSettings(ActionEvent event) {

        try {

            String fxmlPath = "../view/userSettings.fxml";
            FXMLLoader loader = new FXMLLoader(
                    PreferencesCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((UserSettingsCtrl) loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(PreferencesCtrl.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoAbout(ActionEvent event) {

        try {

            String fxmlPath = "../view/about.fxml";
            FXMLLoader loader = new FXMLLoader(PreferencesCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((AboutCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(PreferencesCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void gotoLogin(ActionEvent event) {

        Session.getInstance().close();

        try {

            String fxmlPath = "../view/login.fxml";
            FXMLLoader loader = new FXMLLoader(PreferencesCtrl.class.getResource(fxmlPath));

            Parent page = (Parent) loader.load();
            ((LoginCtrl)loader.getController()).setStage(stage);

            replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(PreferencesCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void quitApplication(ActionEvent event) {
        stage.close();
    }

    @FXML
    void gotoPreferences(ActionEvent event) {

        // Do nothing, we are already here
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        setAdminMenusVisible(Session.getInstance().getAuthenticatedUser().isAdmin());

        scoreData.addAll(DatabaseDataStorage.getInstance().getScoreThresholds());
        populateScoreTable();
    }

    private void setAdminMenusVisible(boolean isAdmin) {
        if(isAdmin) {
            newUserMenuItem.setVisible(true);
            manageAllUsersMenuItem.setVisible(true);
            userMenuSeperator.setVisible(true);
            preferencesMenuItem.setVisible(true);
            preferencesMenuSeperator.setVisible(true);
        } else {
            newUserMenuItem.setVisible(false);
            manageAllUsersMenuItem.setVisible(false);
            userMenuSeperator.setVisible(false);
            preferencesMenuItem.setVisible(false);
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

        Callback<TableColumn, TableCell> cellFactory =
            new Callback<TableColumn, TableCell>() {

                @Override
                public TableCell call(TableColumn p) {
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
                ((ScoreThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEarlyMin((int) t.getNewValue());
            }
        });

        earlyMaxCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ScoreThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ScoreThresholdEntity, Number> t) {
                ((ScoreThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEarlyMax((int)t.getNewValue());
            }
        });

        lateMinCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ScoreThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ScoreThresholdEntity, Number> t) {
                ((ScoreThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLateMin((int)t.getNewValue());
            }
        });

        lateMaxCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ScoreThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ScoreThresholdEntity, Number> t) {
                ((ScoreThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLateMax((int)t.getNewValue());
            }
        });

        scoreCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ScoreThresholdEntity, Number>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ScoreThresholdEntity, Number> t) {
                ((ScoreThresholdEntity) t.getTableView().getItems().get(t.getTablePosition().getRow())).setScoreValue((int)t.getNewValue());
            }
        });


    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Parent replaceSceneContent(Parent page) throws Exception {

        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, 640, 480);

            // TODO no CSS yet
            //scene.getStylesheets().add(App.class.getResource("demo.css").toExternalForm());

            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }

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
