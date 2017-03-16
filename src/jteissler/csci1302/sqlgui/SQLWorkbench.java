package jteissler.csci1302.sqlgui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author J Teissler
 * @date 3/15/17
 */
public class SQLWorkbench
{
	private Stage preferencesStage;

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

	@FXML
	private TextArea commandField;

	@FXML
	private ListView commandLog;

	@FXML
	private ListView statusLog;

	@FXML
	private void initialize()
	{

	}

	@FXML
	private void onRunSingleCommandPressed(ActionEvent event)
	{
		System.out.println(new CommandSelector(commandField).getCommand());
	}

	@FXML
	private void onRunAllCommandsPressed(ActionEvent event){}

	@FXML
	private void onClearCommandsPressed(ActionEvent event){}
	

	@FXML
	private void onAboutPressed(ActionEvent event){}

	@FXML
	private void onOpenScript(ActionEvent event){

		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
				"Sql Scripts", "*." + WorkbenchOptions.SAVE_FILE_EXTENSION);

		fc.getExtensionFilters().add(filter);


		File returnVal = fc.showOpenDialog(parentWindow);
		if(returnVal == null){
			return;

		}
		try {

		Scanner inFile = new Scanner(returnVal);

		String someString = "";

		if(inFile.hasNext()) {
		someString = inFile.useDelimiter("\\Z").next();
		}

		commandField.setText(someString);

		}catch(Exception e){
			e.printStackTrace();
		}

		}

	@FXML
	private void setParent(Window parent){parentWindow = parent;}

	@FXML
	private void onRunScript(ActionEvent event){}

	@FXML
	private void onSaveScript(ActionEvent event){}

	@FXML
	private void onCut(ActionEvent event){commandField.cut();}

	@FXML
	private void onCopy(ActionEvent event){commandField.copy();}

	@FXML
	private void onPaste(ActionEvent event){commandField.paste();}

	@FXML
	private void onUndo(ActionEvent event){commandField.undo();}

	@FXML
	private void onRedo(ActionEvent event){commandField.redo();}

	@FXML
	private void onPreferences(ActionEvent event)
	{
		if (preferencesStage == null)
		{
			try
			{
				Parent root = FXMLLoader.load(getClass().getResource("resources/preferences.fxml"));
				Scene scene = new Scene(root, 600, 400);
				preferencesStage = new Stage();
				preferencesStage.setScene(scene);
				String css = getClass().getResource("resources/Workbench.css").toExternalForm();
				scene.getStylesheets().add(css);
				preferencesStage.setTitle("SQL Workbench - Preferences");
				preferencesStage.show();
				preferencesStage.setOnCloseRequest(e -> preferencesStage = null);

				/*
				Equivalent to
				preferencesStage.setOnCloseRequest(new EventHandler<WindowEvent>()
                {
                    @Override
                    public void handle(WindowEvent event)
                    {
                        preferencesStage = null;
                    }
                });

				 */

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}


		}
	}


}
