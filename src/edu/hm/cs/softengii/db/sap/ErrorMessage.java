package edu.hm.cs.softengii.db.sap;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class ErrorMessage {

    public static void show(Exception e) {

    	Platform.runLater(() ->
    	{
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Error");
	    	alert.setHeaderText("Database error occurred");
	    	alert.setContentText("Database connection failed!");

	    	Label label = new Label("The exception stacktrace was:");

	    	TextArea textArea = new TextArea(convertExceptionToString(e));
	    	textArea.setEditable(false);

	    	textArea.setMaxWidth(Double.MAX_VALUE);
	    	textArea.setMaxHeight(Double.MAX_VALUE);
	    	GridPane.setVgrow(textArea, Priority.ALWAYS);
	    	GridPane.setHgrow(textArea, Priority.ALWAYS);

	    	GridPane expContent = new GridPane();
	    	expContent.setMinWidth(700);
	    	expContent.add(label, 0, 0);
	    	expContent.add(textArea, 0, 1);

	    	alert.getDialogPane().setExpandableContent(expContent);
	    	alert.showAndWait();
    	});
    }

    private static String convertExceptionToString(final Exception e) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	e.printStackTrace(pw);
    	return sw.toString();
    }
}