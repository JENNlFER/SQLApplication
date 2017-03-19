package jteissler.csci1302.sqlgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


/**
 * Controller class for the preferences window. <br>
 *
 * An instance of this class is automatically created whenever the preferences
 * window is opened by the user. <br>
 *
 * Preferences actions are listened to by this class and then the related action
 * change is immediately stored in {@link WorkbenchOptions}.
 *
 * @author J Teissler & Kyle Turner
 *         Created on 3/15/17
 */
public class SQLPreferences
{
    /** Check box for whether highlighted commands are executed */
    @FXML
    private CheckBox execHighlighted;

    /** Check box for whether to print to a status log */
    @FXML
    private CheckBox sqlPrintStatus;

    /** Check box for whether to print to an error log */
    @FXML
    private CheckBox sqlPrintError;

    /** Text field to specify the table file extension */
    @FXML
    private TextField tableFileExt;

    /** Text field to specify the save file extension */
    @FXML
    private TextField savedFileExt;

    /** Text field to specify the directory that data is stored int */
    @FXML
    private TextField masterDirectoryName;

    /** Radio Button to select command separation by semicolons */
    @FXML
    private RadioButton semiButton;

    /** Radio button to select command separation by empty lines */
    @FXML
    private RadioButton emptyLineButton;

    /** Radio button to select command separation by new line characters */
    @FXML
    private RadioButton eachLineButton;

    /**
     * Loads the preferences that are currently stored in the workbench options object.
     * Let it be noted that the program stores options persistently, and will only reset
     * options to the default state if a backing store cannot be created on the system.
     */
    @FXML
    private void initialize()
    {
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

    /**
     * Handles when the {@link SQLPreferences#execHighlighted} object fires an event.
     */
    @FXML
    private void onExecHighlightedToggle(ActionEvent event)
    {
        WorkbenchOptions.setHighlightedCommands(execHighlighted.isSelected());
    }

    /**
     * Handles when the {@link SQLPreferences#sqlPrintStatus} object fires an event.
     */
    @FXML
    private void onPrintStatusToggle(ActionEvent event)
    {
        WorkbenchOptions.setStatusLog(sqlPrintStatus.isSelected());
    }

    /**
     * Handles when the {@link SQLPreferences#sqlPrintError} object fires an event.
     */
    @FXML
    private void onPrintErrorToggle(ActionEvent event)
    {
        WorkbenchOptions.setErrorLog(sqlPrintError.isSelected());
    }

    /**
     * Handles when the {@link SQLPreferences#tableFileExt} object fires an event.
     */
    @FXML
    private void onTableKeyTyped(KeyEvent event)
    {
        // If an extension has been entered
        if (tableFileExt.getText() != null)
        {
            // Store the entered table file extension
            WorkbenchOptions.setTableExtension(tableFileExt.getText());
        }
        else
        {
            // Otherwise use the default extension
            WorkbenchOptions.setTableExtension("tab");
        }

    }

    /**
     * Handles when the {@link SQLPreferences#savedFileExt} object fires an event.
     */
    @FXML
    private void onSavedKeyTyped(KeyEvent event)
    {
        // If an extension has been entered
        if (savedFileExt.getText() != null)
        {
            // Store the entered save file extension
            WorkbenchOptions.setSaveExtension(savedFileExt.getText());
        }
        else
        {
            // Otherwise use the default extension
            WorkbenchOptions.setSaveExtension("sql");
        }
    }

    /**
     * Handles when the {@link SQLPreferences#masterDirectoryName} object fires an event.
     */
    @FXML
    private void onMasterDirectoryKeyTyped(KeyEvent event)
    {
        // If a directory name has been entered
        if (masterDirectoryName.getText() != null && !(tableFileExt.getText().isEmpty()))
        {
            // Store the entered master directory name
            WorkbenchOptions.setMasterFile(masterDirectoryName.getText());
        }
        else
        {
            // Otherwise use the default directory
            WorkbenchOptions.setMasterFile("sql_data");
        }
    }

    /*

     */

    /**
     * Handles when the {@link SQLPreferences#semiButton} object fires an event.
     *
     * Includes functionality for the radio buttons to work like radio buttons, such that only one can
     * be selected a time. In this case, if one of the three radio buttons is clicked, then the others
     * are automatically unselected.
     */
    @FXML
    private void onSemiToggle(ActionEvent event)
    {
        //checks if either of the other buttons are selected... if so it unselects them.
        if (emptyLineButton.isSelected()) emptyLineButton.setSelected(false);
        if (eachLineButton.isSelected()) eachLineButton.setSelected(false);

        WorkbenchOptions.setCommandDivider(WorkbenchOptions.CommandDivider.SEMICOLON);

    }

    /**
     * Handles when the {@link SQLPreferences#emptyLineButton} object fires an event.
     *
     * Includes functionality for the radio buttons to work like radio buttons, such that only one can
     * be selected a time. In this case, if one of the three radio buttons is clicked, then the others
     * are automatically unselected.
     */
    @FXML
    private void onEmptyLineToggle(ActionEvent event)
    {
        if (semiButton.isSelected()) semiButton.setSelected(false);
        if (eachLineButton.isSelected()) eachLineButton.setSelected(false);

        WorkbenchOptions.setCommandDivider(WorkbenchOptions.CommandDivider.EMPTY_LINE);
    }

    /**
     * Handles when the {@link SQLPreferences#eachLineButton} object fires an event.
     *
     * Includes functionality for the radio buttons to work like radio buttons, such that only one can
     * be selected a time. In this case, if one of the three radio buttons is clicked, then the others
     * are automatically unselected.
     */
    @FXML
    private void onEachLineToggle(ActionEvent event)
    {
        if (emptyLineButton.isSelected()) emptyLineButton.setSelected(false);
        if (semiButton.isSelected()) semiButton.setSelected(false);

        WorkbenchOptions.setCommandDivider(WorkbenchOptions.CommandDivider.EACH_LINE);

    }
}