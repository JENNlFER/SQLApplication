package jteissler.csci1302.sqlgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import jteissler.csci1302.simplesql.AssignmentParser;
import jteissler.csci1302.simplesql.Log;
import jteissler.csci1302.simplesql.Pair;
import jteissler.csci1302.simplesql.Parser;
import jteissler.csci1302.simplesql.SQLDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author J Teissler
 * @date 3/15/17
 */
public class SQLWorkbench
{
	private Stage preferencesStage;
	private Stage aboutStage;
	private CommandSelector selector;
	private Parser sql;

	private Image success;
	private Image failure;

	@FXML
	private Window parentWindow;

	@FXML
	private MenuItem openScript;

	@FXML
	private MenuItem runScript;

	@FXML
	private MenuItem saveScript;

	@FXML
	private MenuItem cut;

	@FXML
	private MenuItem copy;

	@FXML
	private MenuItem paste;

	@FXML
	private MenuItem preferences;

	@FXML
	private MenuItem undo;

	@FXML
	private MenuItem redo;

	@FXML
	private MenuItem about;

	@FXML
	private Button runSingleCommand;

	@FXML
	private Button runAllCommands;

	@FXML
	private Button clearCommands;

	@FXML
	private TreeView<String> structure;
	private TreeItem<String> structureRoot;

	@FXML
	private TextArea commandField;

	@FXML
	private TextArea outputField;

	@FXML
	private ListView<HBox> commandLog;
	private ObservableList<HBox> commands = FXCollections.observableArrayList();

	@FXML
	private void initialize()
	{
		success = new Image(getClass().getResourceAsStream("resources/status.png"));
		failure = new Image(getClass().getResourceAsStream("resources/error.png"));

		sql = new AssignmentParser(new SQLDatabase());
		selector = new CommandSelector(commandField);

		commandLog.setItems(commands);


		Log.setStatusWriter((command, status) ->
		{
			ImageView view = new ImageView(success);
			view.setFitHeight(18);
			view.setFitWidth(18);
			HBox box = new HBox(view, new Label("[" + (commands.size() + 1) + "]"), new Label(command + ";"));
			box.setSpacing(10);
			commands.add(box);
			commandLog.scrollTo(commands.size() - 1);

			outputField.clear();
			outputField.appendText(status);
		});

		Log.setErrorWriter((command, status) ->
		{
			if (!status.equals("No command entered."))
			{
				ImageView view = new ImageView(failure);
				view.setFitHeight(18);
				view.setFitWidth(18);
				HBox box = new HBox(view, new Label("[" + (commands.size() + 1) + "]"), new Label(command + ";"));
				box.setSpacing(10);
				commands.add(box);
				commandLog.scrollTo(commands.size() - 1);
			}

			outputField.clear();
			outputField.appendText(status);
		});

		structureRoot = new TreeItem<>("SQL Database Schema");
		structure.setRoot(structureRoot);
		walkDirectoryTree();
		structureRoot.setExpanded(true);
	}

	@FXML
	private void onRunSingleCommandPressed(ActionEvent event)
	{
		sql.parse(selector.getCommand());
		walkDirectoryTree();
	}

	@FXML
	private void onRunAllCommandsPressed(ActionEvent event)
	{
		sql.parse(selector.getAllCommands());
		walkDirectoryTree();
	}

	@FXML
	private void onClearCommandsPressed(ActionEvent event)
	{
		commandField.clear();
	}

	private void walkDirectoryTree()
	{
		structureRoot.getChildren().clear();
		File dir = new File(WorkbenchOptions.getMasterFile());
		File[] files = dir.listFiles();

		if (files == null)
		{
			return;
		}

		for (File file : files)
		{
			if (file.isDirectory())
			{
				TreeItem<String> database = new TreeItem<>(file.getName());
				File[] subFiles = file.listFiles();

				for (File subFile : subFiles)
				{
					if (subFile.isFile() && subFile.getName().endsWith("." + WorkbenchOptions.getTableExtension()))
					{
						database.getChildren().add(new TreeItem<>(subFile.getName().replace("." + WorkbenchOptions.getTableExtension(), "")));
					}
				}

				structureRoot.getChildren().add(database);
				database.setExpanded(true);
			}
		}
	}

	@FXML
	private void onAboutPressed(ActionEvent event)
	{
		if (aboutStage == null)
		{
			try
			{
				Parent root = FXMLLoader.load(getClass().getResource("resources/about.fxml"));
				Scene scene = new Scene(root, 600, 400);
				aboutStage = new Stage();
				aboutStage.setScene(scene);
				String css = getClass().getResource("resources/workbench.css").toExternalForm();
				scene.getStylesheets().add(css);
				aboutStage.initOwner(parentWindow);
				aboutStage.initModality(Modality.APPLICATION_MODAL);
				aboutStage.setTitle("SQL Workbench - About");
				aboutStage.setOnCloseRequest(e -> preferencesStage = null);
				aboutStage.setResizable(false);
				aboutStage.show();



			}
			catch (IOException e)
			{
				e.printStackTrace();
			}


		}
	}

	@FXML
	private void onOpenScript(ActionEvent event)
	{

		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Sql Scripts", "*." + WorkbenchOptions.getSaveExtension());

		fc.getExtensionFilters().add(filter);


		File returnVal = fc.showOpenDialog(parentWindow);
		if (returnVal == null)
		{
			return;

		}
		try
		{

			Scanner inFile = new Scanner(returnVal);

			String someString = "";

			if (inFile.hasNext())
			{
				someString = inFile.useDelimiter("\\Z").next();
			}

			commandField.setText(someString);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@FXML
	private void setParent(Window parent)
	{
		parentWindow = parent;
	}

	@FXML
	private void onRunScript(ActionEvent event)
	{

		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Sql Scripts", "*." + WorkbenchOptions.getSaveExtension());

		fc.getExtensionFilters().add(filter);


		File returnVal = fc.showOpenDialog(parentWindow);
		if (returnVal == null)
		{
			return;

		}
		try
		{

			Scanner inFile = new Scanner(returnVal);

			String someString = "";

			if (inFile.hasNext())
			{
				someString = inFile.useDelimiter("\\Z").next();
			}

			commandField.setText(someString);

			sql.parse(selector.getAllCommands());
			walkDirectoryTree();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}


	@FXML
	private void onSaveScript(ActionEvent event)
	{

		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Sql Scripts", "*." + WorkbenchOptions.getSaveExtension());

		fc.getExtensionFilters().add(filter);

		File returnVal = fc.showSaveDialog(parentWindow);

		if (returnVal == null)
		{
			return;
		}

		PrintWriter writer = null;
		try
		{
			String someString = commandField.getText();
			writer = new PrintWriter(new FileWriter(returnVal, true));


			writer.print(someString);

			writer.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (writer != null)
			{
				writer.close();
			}
		}

	}


	@FXML
	private void onCut(ActionEvent event)
	{
		commandField.cut();
	}

	@FXML
	private void onCopy(ActionEvent event)
	{
		commandField.copy();
	}

	@FXML
	private void onPaste(ActionEvent event)
	{
		commandField.paste();
	}

	@FXML
	private void onUndo(ActionEvent event)
	{
		commandField.undo();
	}

	@FXML
	private void onRedo(ActionEvent event)
	{
		commandField.redo();
	}

	@FXML
	private void onPreferences(ActionEvent event)
	{
		if (preferencesStage == null)
		{
			try
			{
				Parent root = FXMLLoader.load(getClass().getResource("resources/preferences.fxml"));
				Scene scene = new Scene(root, 517, 331);
				preferencesStage = new Stage();
				preferencesStage.setScene(scene);
				String css = getClass().getResource("resources/workbench.css").toExternalForm();
				scene.getStylesheets().add(css);
				preferencesStage.initOwner(parentWindow);
				preferencesStage.initModality(Modality.APPLICATION_MODAL);
				preferencesStage.setTitle("SQL Workbench - Preferences");
				preferencesStage.setResizable(false);
				preferencesStage.setOnCloseRequest(e -> {preferencesStage = null; walkDirectoryTree();});
				preferencesStage.show();


			}
			catch (IOException e)
			{
				e.printStackTrace();
			}


		}
	}


}
