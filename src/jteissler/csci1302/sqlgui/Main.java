package jteissler.csci1302.sqlgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		new WorkbenchOptions();

		Parent root = FXMLLoader.load(getClass().getResource("resources/workbench.fxml"));
		primaryStage.setTitle("SQL Workbench");
		Scene scene = new Scene(root, 800, 600);
		String css = getClass().getResource("resources/workbench.css").toExternalForm();
		scene.getStylesheets().add(css);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void main(String[] args)
	{
		launch(args);
	}
}
