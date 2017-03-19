package jteissler.csci1302.sqlgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main, launcher for the SQL Application. This loads the main workbench window.
 *
 * @author J Teissler & Kyle Turner
 *         Created on 3/15/17
 */
public class Main extends Application
{
	@Override
	public void start(Stage stage) throws Exception
	{
		// Initializes persistent preferences
		new WorkbenchOptions();

		// Set up the workbench window
		Parent root = FXMLLoader.load(getClass().getResource("resources/workbench.fxml"));
		stage.setTitle("SQL Workbench");
		Scene scene = new Scene(root, 800, 600);

		// Load in and add style sheets
		String css = getClass().getResource("resources/workbench.css").toExternalForm();
		scene.getStylesheets().add(css);

		// Set the window dimensions and display the scene.
		stage.setMinWidth(580);
		stage.setMinHeight(350);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Main class called by the Java launcher.
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
}
