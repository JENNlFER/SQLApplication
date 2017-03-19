package jteissler.csci1302.sqlgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import jteissler.csci1302.simplesql.AssignmentParser;
import jteissler.csci1302.simplesql.Log;
import jteissler.csci1302.simplesql.Parser;
import jteissler.csci1302.simplesql.SQLDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The controller for the main scene of the SQL Workbench.
 *
 * @author J Teissler & Kyle Turner
 *         Created on 3/15/17
 */
public class SQLWorkbench
{
	/** Stores the preferences stage when it is open */
	private Stage preferencesStage;

	/** Stores the about stage when it is open */
	private Stage aboutStage;

	/** The command selector which gets commands from the text area */
	private CommandSelector selector;

	/** An instance of the SQL parser */
	private Parser sql;

	/** The "command succeeded" image */
	private Image success;

	/** The "command failed" image */
	private Image failure;

	/** The main window which is parent to any child windows created by this class */
	@FXML
	private Window parentWindow;

	/** A menu option which triggers an open file dialog */
	@FXML
	private MenuItem openScript;

	/** A menu option which triggers an open file dialog, then runs that file */
	@FXML
	private MenuItem runScript;

	/** A menu option which triggers a save file dialog */
	@FXML
	private MenuItem saveScript;

	/** A menu option which cuts any selected text (to the clipboard) in the command entry field */
	@FXML
	private MenuItem cut;

	/** A menu option which copies any selected text (to the clipboard) in the command entry field */
	@FXML
	private MenuItem copy;

	/** A menu option which pastes any text in the clipboard to the command entry field */
	@FXML
	private MenuItem paste;

	/** A menu option which opens the preferences window */
	@FXML
	private MenuItem preferences;

	/** A menu option which triggers the undo action on any text edits in the command entry field */
	@FXML
	private MenuItem undo;

	/** A menu option which triggers the redo action on and undone text edits in the command entry field */
	@FXML
	private MenuItem redo;

	/** A menu option which opens the about window */
	@FXML
	private MenuItem about;

	/** A button which will run a single command in the command entry field */
	@FXML
	private Button runSingleCommand;

	/** A button which will run all of the commands in the command entry field */
	@FXML
	private Button runAllCommands;

	/** A button which clears all commands from the command entry field */
	@FXML
	private Button clearCommands;

	/** The tree which displays the database structure */
	@FXML
	private TreeView<String> structure;

	/** The immutable root of the tree */
	private TreeItem<String> structureRoot;

	/** The text area in which commands are entered */
	@FXML
	private TextArea commandField;

	/** The text area which displays the result of the last command executed */
	@FXML
	private TextArea outputField;

	/** A list view which holds a log of all commands executed in a session */
	@FXML
	private ListView<HBox> commandLog;

	/** The backing list of items in the command log list view */
	private ObservableList<HBox> commands = FXCollections.observableArrayList();

	/**
	 * Loads any required resources for the application, adds handlers for logging,
	 * prepares the directory tree, and creates the SQL database which this program interfaces with.
	 */
	@FXML
	private void initialize()
	{
		// Load images
		success = new Image(getClass().getResourceAsStream("resources/status.png"));
		failure = new Image(getClass().getResourceAsStream("resources/error.png"));

		// Create the SQL database and hook it to a parser
		sql = new AssignmentParser(new SQLDatabase());

		// Create a command selector for the text field.
		selector = new CommandSelector(commandField);

		// Associate the command log's backing list with it
		commandLog.setItems(commands);

		// Set how status messages are to be displayed by the logger in the application GUI
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

		// Set how error messages are to be displayed by the logger in the application GUI
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

		// Give the database structure view a root node.
		structureRoot = new TreeItem<>("SQL Database Schema");
		structure.setRoot(structureRoot);
		structureRoot.setExpanded(true);

		// Update directory tree if anything is pre-existing
		walkDirectoryTree();
	}

	/**
	 * Handle events fired by {@link SQLWorkbench#runSingleCommand}.
	 */
	@FXML
	private void onRunSingleCommandPressed(ActionEvent event)
	{
		sql.parse(selector.getCommand());
		walkDirectoryTree();
	}

	/**
	 * Handle events fired by {@link SQLWorkbench#runAllCommands}.
	 */
	@FXML
	private void onRunAllCommandsPressed(ActionEvent event)
	{
		sql.parse(selector.getAllCommands());
		walkDirectoryTree();
	}

	/**
	 * Handle events fired by {@link SQLWorkbench#clearCommands}.
	 */
	@FXML
	private void onClearCommandsPressed(ActionEvent event)
	{
		commandField.clear();
	}

	/**
	 * Walks through all first-level directories and files in the master directory and updates the directory tree.
	 */
	private void walkDirectoryTree()
	{
		// Clear out the old data from the directory tree display
		structureRoot.getChildren().clear();
		File dir = new File(WorkbenchOptions.getMasterFile());
		File[] files = dir.listFiles();

		if (files == null)
		{
			return;
		}

		// Iterate over all sub-directories
		for (File file : files)
		{
			if (file.isDirectory())
			{
				// Create a database node for each sub-directory
				TreeItem<String> database = new TreeItem<>(file.getName());
				File[] subFiles = file.listFiles();

				// Iterate over all files in the subdirectories
				for (File subFile : subFiles)
				{
					// Add any table files the corresponding sub-directory node.
					if (subFile.isFile() && subFile.getName().endsWith("." + WorkbenchOptions.getTableExtension()))
					{
						database.getChildren().add(new TreeItem<>(subFile.getName().replace("." + WorkbenchOptions.getTableExtension(), "")));
					}
				}

				// Add the sub-directory node as a child of the root node
				structureRoot.getChildren().add(database);
				database.setExpanded(true);
			}
		}
	}

	/**
	 * Handle events fired by {@link SQLWorkbench#about} and opens the about window.
	 */
	@FXML
	private void onAboutPressed(ActionEvent event)
	{
		// Only open a window if one is not already open.
		if (aboutStage == null)
		{
			try
			{
				// Load the FXML document
				Parent root = FXMLLoader.load(getClass().getResource("resources/about.fxml"));
				Scene scene = new Scene(root, 600, 400);
				aboutStage = new Stage();
				aboutStage.setScene(scene);

				// Load the style sheet and assign it to the fxml layout
				String css = getClass().getResource("resources/workbench.css").toExternalForm();
				scene.getStylesheets().add(css);

				// Set the about window as a child of the parent window so it blocks the parent.
				aboutStage.initOwner(parentWindow);
				aboutStage.initModality(Modality.APPLICATION_MODAL);

				// Configure the stage, and display it
				aboutStage.setTitle("SQL Workbench - About");
				aboutStage.setOnCloseRequest(e -> preferencesStage = null);
				aboutStage.setResizable(false);
				aboutStage.show();
			}
			catch (IOException e)
			{
				// Display a pop-up if an exception occurred.
				new ExceptionPopUp(e);
			}
		}
	}

	/**
	 * Handle events fired by {@link SQLWorkbench#openScript}. <br>
	 *
	 * This will open the file chooser, allowing the user to select a script to open, then display that in the editor.
	 */
	@FXML
	private void onOpenScript(ActionEvent event)
	{
		// Uses the FileChooser class to open the window to select files to choose from.
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Sql Scripts", "*." + WorkbenchOptions.getSaveExtension());

		// Adds a filter to look for a specific file
		fc.getExtensionFilters().add(filter);

		// Shows the dialog box to choose the files from
		File returnVal = fc.showOpenDialog(parentWindow);

		// If no file was selected, return.
		if (returnVal == null)
		{
			return;
		}

		Scanner inFile = null;
		try
		{
			// Reads the file with a scanner
			inFile = new Scanner(returnVal);
			String someString = "";

			if (inFile.hasNext())
			{
				someString = inFile.useDelimiter("\\Z").next();
			}

			// Sets the text from the script inside the commandField
			commandField.setText(someString);

		}
		catch (Exception e)
		{
			// Display a pop-up if an exception occurred.
			new ExceptionPopUp(e);
		}
		finally
		{
			// Close the file
			if (inFile != null)
			{
				inFile.close();
			}
		}

	}

	/**
	 * Sets the parent window of this class.
	 */
	@FXML
	private void setParent(Window parent)
	{
		parentWindow = parent;
	}

	/**
	 * Handle events fired by {@link SQLWorkbench#runScript}. <br>
	 *
	 * This will open the file chooser, allowing the user to select a script to open, display that script in the
	 * editor, then execute the contents of the script.
	 */
	@FXML
	private void onRunScript(ActionEvent event)
	{

		// Uses the FileChooser class to open the window to select files to choose from.
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Sql Scripts", "*." + WorkbenchOptions.getSaveExtension());

		// Adds a filter to look for a specific file
		fc.getExtensionFilters().add(filter);

		// Shows the dialog box to choose the files from
		File returnVal = fc.showOpenDialog(parentWindow);

		// If no file was selected, return.
		if (returnVal == null)
		{
			return;

		}

		Scanner inFile = null;
		try
		{
			// Reads the file with a scanner
			inFile = new Scanner(returnVal);

			String someString = "";

			if (inFile.hasNext())
			{
				someString = inFile.useDelimiter("\\Z").next();
			}

			// Sets the text from the script inside the commandField
			commandField.setText(someString);

			// Executes the command.
			sql.parse(selector.getAllCommands());

			// Updates the directory tree.
			walkDirectoryTree();

		}
		catch (Exception e)
		{
			// Display a pop-up if an exception occurred.
			new ExceptionPopUp(e);
		}
		finally
		{
			// Close the file
			if (inFile != null)
			{
				inFile.close();
			}
		}
	}

	/**
	 * Handle events fired by {@link SQLWorkbench#saveScript}. <br>
	 *
	 * This will open the a save dialog, allowing the user to name and save the script to the computer.
	 */
	@FXML
	private void onSaveScript(ActionEvent event)
	{
		// Uses the FileChooser class to open the window to save the script.
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Sql Scripts", "*." + WorkbenchOptions.getSaveExtension());

		// Adds a filter to save as a specific file type.
		fc.getExtensionFilters().add(filter);

		// Shows the save dialog box.
		File returnVal = fc.showSaveDialog(parentWindow);

		// If no file was chosen, return.
		if (returnVal == null)
		{
			return;
		}

		PrintWriter writer = null;
		try
		{
			// Write the commands to file.
			String someString = commandField.getText();
			writer = new PrintWriter(new FileWriter(returnVal, true));
			writer.print(someString);

		}
		catch (Exception e)
		{
			// SHow a pop-up if an exception occurred.
			new ExceptionPopUp(e);
		}
		finally
		{
			// Close the writer.
			if (writer != null)
			{
				writer.close();
			}
		}

	}

	/*
		The following commands work just like windows' version of cut,copy,paste,undo,redo
	 */

	/** cuts the commandField text that is highlighted */
	@FXML
	private void onCut(ActionEvent event)
	{
		commandField.cut();
	}

	/** copies the commandField text that is highlighted */
	@FXML
	private void onCopy(ActionEvent event)
	{
		commandField.copy();
	}

	/** pastes the text that is stored in the copy/cut to the command field where the cursor is */
	@FXML
	private void onPaste(ActionEvent event)
	{
		commandField.paste();
	}

	/** undo the previous action done to the commandfield */
	@FXML
	private void onUndo(ActionEvent event)
	{
		commandField.undo();
	}

	/** redo the previous action done to the commandField */
	@FXML
	private void onRedo(ActionEvent event)
	{
		commandField.redo();
	}

	/** opens the preferences window works much like the main class method start() that sets up the SQL Workbench */
	@FXML
	private void onPreferences(ActionEvent event)
	{
		// Only open a new preferences window if one is not already open.
		if (preferencesStage == null)
		{
			try
			{
				// Load the FXML document
				Parent root = FXMLLoader.load(getClass().getResource("resources/preferences.fxml"));
				Scene scene = new Scene(root, 517, 331);
				preferencesStage = new Stage();
				preferencesStage.setScene(scene);

				// Load the style sheets and style the FXML
				String css = getClass().getResource("resources/workbench.css").toExternalForm();
				scene.getStylesheets().add(css);

				// Set the parent of the preferences window
				preferencesStage.initOwner(parentWindow);
				preferencesStage.initModality(Modality.APPLICATION_MODAL);

				// Configure the window and display it.
				preferencesStage.setTitle("SQL Workbench - Preferences");
				preferencesStage.setResizable(false);
				preferencesStage.setOnCloseRequest(e -> {preferencesStage = null; walkDirectoryTree();});
				preferencesStage.show();
			}
			catch (IOException e)
			{
				// Display a pop-up if an exception occurred.
				new ExceptionPopUp(e);
			}
		}
	}
}
