package jteissler.csci1302.sqlgui;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Pops up a dialog with an error message and a stack trace.
 *
 * @author J Teissler
 * @date 3/19/17
 */
public class ExceptionPopUp
{
	/**
	 * Create an exception pop-up.
	 *
	 * @param e The exception which caused the error.
	 */
	public ExceptionPopUp(Exception e)
	{
		// Create a popup
		Alert popup = new Alert(Alert.AlertType.ERROR);
		popup.setTitle("SQL Workbench Error");
		popup.setHeaderText(e.getMessage());

		// Write the exception text into a text area.
		PrintWriter writer = new PrintWriter(new StringWriter());
		e.printStackTrace(writer);
		TextArea trace = new TextArea(e.toString());

		// Configure properties of the text area
		trace.setWrapText(true);
		trace.setEditable(false);
		trace.setMaxWidth(Double.MAX_VALUE);
		trace.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(trace, Priority.ALWAYS);
		GridPane.setHgrow(trace, Priority.ALWAYS);

		// Create a grid to display the trace in
		GridPane expansion = new GridPane();
		expansion.setMaxWidth(Double.MAX_VALUE);

		// Create a label to explain the trace
		Label label = new Label("StackTrace:");

		// Add the trace and label to the grid
		expansion.add(label, 0, 0);
		expansion.add(trace, 0, 1);

		// Add the content to the pop-up and display it.
		popup.getDialogPane().setExpandableContent(expansion);
		popup.showAndWait();
	}
}
