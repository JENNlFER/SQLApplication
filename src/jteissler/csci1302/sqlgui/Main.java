package jteissler.csci1302.sqlgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

/*
	The launcher for the SQL Application. This primarily loads
	the workbench window, the preferences, and about windows are not loaded here.
	They are instead handled in different places in the project.


 */
public class Main extends Application
{
	@Override
	public void start(Stage stage) throws Exception
	{
		new WorkbenchOptions();
		//workbench window
		Parent root = FXMLLoader.load(getClass().getResource("resources/workbench.fxml"));
		stage.setTitle("SQL Workbench");
		Scene scene = new Scene(root, 800, 600);
		//create the style sheets
		String css = getClass().getResource("resources/workbench.css").toExternalForm();
		//adds the style sheets... makes our project actually have the detail and look it does.
		scene.getStylesheets().add(css);
		//sets the windows dimensions
		stage.setMinWidth(580);
		stage.setMinHeight(350);
		stage.setScene(scene);
		//shows the window
		stage.show();
	}

	//actually loads the application you have to have a main class for the project to work properly. This is just javafx's way of
	//launching the application
	public static void main(String[] args)
	{
		launch(args);
	}
}
