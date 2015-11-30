/*
Organisation: Apachen Pub Team
Project: SupplyAlyticsApp
*/

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

/**
 * Provides an Error Message Popup for the application.
 */
public class ErrorMessage {

    /**
     * Shows a popup with the respective error details within the appication.
     * @param exception The thrown exception
     */
    public static void show(Exception exception) {

    	Platform.runLater(() ->
    	{
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Error");
	    	alert.setHeaderText("Database error occurred");
	    	alert.setContentText("Database connection failed!");

	    	Label label = new Label("The exception stacktrace was:");

	    	TextArea textArea = new TextArea(convertExceptionToString(exception));
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

	/**
	 * Creates a String from an Exception stack trace.
	 * @param e Exception to be displayed as string
	 * @return The exception stacktrace as string.
	 */
    private static String convertExceptionToString(final Exception e) {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	e.printStackTrace(pw);
    	return sw.toString();
    }
}