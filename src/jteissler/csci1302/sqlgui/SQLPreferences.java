package jteissler.csci1302.sqlgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.prefs.Preferences;


/**
 * Created by Kyle on 3/15/2017.
 */
public class SQLPreferences
{
    //preferences that are stored
    private Preferences preferences;

    //exechighlighted check box within preferences window
    @FXML
    private CheckBox execHighlighted;

    //sqlprintstatus checkbox within preferences window
    @FXML
    private CheckBox sqlPrintStatus;

    //sqlprinterror checkbox within preferences window
    @FXML
    private CheckBox sqlPrintError;

    //tablefileext textfield within preferences window
    @FXML
    private TextField tableFileExt;

    //
    //like the rest above. These are all exactly related
    ////in the fact that they are used to store a choice for
    ////a user when a user commits an action upon them
    ////like a user typing in the savedfileext textfield
    ////that event is stored by our program
    ////to get what saved file ext the user would like for his
    ////saved files
    //

    @FXML
    private TextField savedFileExt;

    @FXML
    private TextField masterDirectoryName;

    @FXML
    private RadioButton semiButton;

    @FXML
    private RadioButton emptyLineButton;

    @FXML
    private RadioButton eachLineButton;

    /*
        Loads the preferences that are currently stored in workbench options.
        The preferences are reset to default each time the program is terminated, or rather each time the program
        opens
    */
    @FXML
    private void initialize()
    {
        /*
        sets the table file and saved file extensions. Sets the master directory name.
        sets the execHighlighted option to true, sets printstatus and print error logs
        to selected as well by default.
         */
        tableFileExt.setText(WorkbenchOptions.getTableExtension());
        savedFileExt.setText(WorkbenchOptions.getSaveExtension());
        masterDirectoryName.setText(WorkbenchOptions.getMasterFile());

        execHighlighted.setSelected(WorkbenchOptions.doHighlightedCommands());
        sqlPrintStatus.setSelected(WorkbenchOptions.doStatusLog());
        sqlPrintError.setSelected(WorkbenchOptions.doErrorLog());

        switch (WorkbenchOptions.getCommandDivider())
        {

            case SEMICOLON:
                semiButton.setSelected(true);
                break;
            case EACH_LINE:
                eachLineButton.setSelected(true);
                break;
            case EMPTY_LINE:
                emptyLineButton.setSelected(true);
                break;

        }
    }

    //handles if the exec highlighted option in preferences is clicked.
    //if it is clicked, it sets exec highlighted to true, if it's not selected, exec highlighted is false.
    @FXML
    private void onExecHighlightedToggle(ActionEvent event)
    {
        WorkbenchOptions.setHighlightedCommands(execHighlighted.isSelected());
    }

    /*
    works like the method above (onExecHighlightedToggle) if it's clicked, it's true, if it's not clicked it's false
     */
    @FXML
    private void onPrintStatusToggle(ActionEvent event)
    {
        WorkbenchOptions.setStatusLog(sqlPrintStatus.isSelected());
    }

    /*
    works like other methods except for the printerrortoggle
     */
    @FXML
    private void onPrintErrorToggle(ActionEvent event)
    {
        WorkbenchOptions.setErrorLog(sqlPrintError.isSelected());
    }

    //sets the table extension that the user prefers, if changed from default.
    @FXML
    private void onTableKeyTyped(KeyEvent event)
    {
        //if there is text
        if (tableFileExt.getText() != null)
        {
            //set the table file ext
            WorkbenchOptions.setTableExtension(tableFileExt.getText());
        }
        else
        {
            //set to default
            WorkbenchOptions.setTableExtension("tab");
        }

    }

    //works like onTableKeyTyped execept it changes the saved file extension
    @FXML
    private void onSavedKeyTyped(KeyEvent event)
    {
        if (savedFileExt.getText() != null)
        {
            //set the saved file ext
            WorkbenchOptions.setSaveExtension(savedFileExt.getText());
        }
        else
        {
            //set to default
            WorkbenchOptions.setSaveExtension("sql");
        }
    }

    //changes the masterdirectory name
    @FXML
    private void onMasterDirectoryKeyTyped(KeyEvent event)
    {
        if (masterDirectoryName.getText() != null && !(tableFileExt.getText().isEmpty()))
        {
            //sets the master file name
            WorkbenchOptions.setMasterFile(masterDirectoryName.getText());
        }
        else
        {
            //set to default
            WorkbenchOptions.setMasterFile("sql_data");
        }
    }

    /*
        Changes the radio buttons to work like radio buttons. Only one can be selected a time.
        in this case if semitoggle is clicked, then emptyline button and eachline button are not able to be selected
        it also selects the command divider or what separates the commands in the commandfield.
     */
    @FXML
    private void onSemiToggle(ActionEvent event)
    {
        //checks if either of the other buttons are selected... if so it unselects them.
        if (emptyLineButton.isSelected()) emptyLineButton.setSelected(false);
        if (eachLineButton.isSelected()) eachLineButton.setSelected(false);

        WorkbenchOptions.setCommandDivider(WorkbenchOptions.CommandDivider.SEMICOLON);

    }

    //works just like semitoggle execpt for emptylinetoggle
    @FXML
    private void onEmptyLineToggle(ActionEvent event)
    {
        if (semiButton.isSelected()) semiButton.setSelected(false);
        if (eachLineButton.isSelected()) eachLineButton.setSelected(false);

        WorkbenchOptions.setCommandDivider(WorkbenchOptions.CommandDivider.EMPTY_LINE);
    }

    //works just like the other methods except for the each line toggle case.
    @FXML
    private void onEachLineToggle(ActionEvent event)
    {
        if (emptyLineButton.isSelected()) emptyLineButton.setSelected(false);
        if (semiButton.isSelected()) semiButton.setSelected(false);

        WorkbenchOptions.setCommandDivider(WorkbenchOptions.CommandDivider.EACH_LINE);

    }
}