package jteissler.csci1302.sqlgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;

/**
 * @author J Teissler
 * @date 3/15/17
 */
public class SQLWorkbench
{
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
	private void onRunSingleCommandPressed(ActionEvent event){}

	@FXML
	private void onRunAllCommandsPressed(ActionEvent event){}

	@FXML
	private void onClearCommandsPressed(ActionEvent event){}

	@FXML
	private void onCommandFieldTyped(ActionEvent event){}

	@FXML
	private void onAboutPressed(ActionEvent event){}

	@FXML
	private void onOpenScript(ActionEvent event){}

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
	private void onPreferences(ActionEvent event){}


}
