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
	public void start(Stage stage) throws Exception
	{
		new WorkbenchOptions();

		Parent root = FXMLLoader.load(getClass().getResource("resources/workbench.fxml"));
		stage.setTitle("SQL Workbench");
		Scene scene = new Scene(root, 800, 600);
		String css = getClass().getResource("resources/workbench.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setMinWidth(580);
		stage.setMinHeight(350);
		stage.setScene(scene);
		stage.show();
	}


	public static void main(String[] args)
	{
		launch(args);
	}
}
