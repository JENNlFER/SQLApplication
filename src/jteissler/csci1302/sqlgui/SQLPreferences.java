package jteissler.csci1302.sqlgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


/**
 * Created by Kyle on 3/15/2017.
 */
public class SQLPreferences
{


    @FXML
    private CheckBox execHighlighted;

    @FXML
    private CheckBox sqlPrintStatus;

    @FXML
    private CheckBox sqlPrintError;

    @FXML
    private TextField tableFileExt;

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

    @FXML
    private void initialize()
    {

        tableFileExt.setText(WorkbenchOptions.TABLE_FILE_EXTENSION);
        savedFileExt.setText(WorkbenchOptions.SAVE_FILE_EXTENSION);
        masterDirectoryName.setText(WorkbenchOptions.MASTER_DIRECTORY);

        execHighlighted.setSelected(WorkbenchOptions.EXECUTE_HIGHLIGHTED);
        sqlPrintStatus.setSelected(WorkbenchOptions.USE_STATUS_LOG);
        sqlPrintStatus.setSelected(WorkbenchOptions.USE_ERROR_LOG);

        switch(WorkbenchOptions.COMMAND_DIVIDER){

            case SEMICOLON:
                semiButton.setSelected(true);
                break;
            case EACH_LINE:
                emptyLineButton.setSelected(true);
                break;
            case EMPTY_LINE:
                eachLineButton.setSelected(true);
                break;

        }
    }


    @FXML
    private void onExecHighlightedToggle(ActionEvent event)
    {
        WorkbenchOptions.EXECUTE_HIGHLIGHTED = execHighlighted.isSelected();
    }

    @FXML
    private void onPrintStatusToggle(ActionEvent event)
    {
        WorkbenchOptions.USE_STATUS_LOG = sqlPrintStatus.isSelected();
    }

    @FXML
    private void onPrintErrorToggle(ActionEvent event)
    {
        WorkbenchOptions.USE_ERROR_LOG = sqlPrintError.isSelected();
    }

    @FXML
    private void onTableKeyTyped(KeyEvent event)
    {
        if (tableFileExt.getText() != null && !(tableFileExt.getText().isEmpty()))
        {
            WorkbenchOptions.TABLE_FILE_EXTENSION = tableFileExt.getText();
        } else
        {
            WorkbenchOptions.TABLE_FILE_EXTENSION = "tab";
        }

    }

    @FXML
    private void onSavedKeyTyped(KeyEvent event)
    {
        if (savedFileExt.getText() != null && !(tableFileExt.getText().isEmpty()))
        {
            WorkbenchOptions.SAVE_FILE_EXTENSION = savedFileExt.getText();
        } else
        {
            WorkbenchOptions.SAVE_FILE_EXTENSION = "sql";
        }
    }

    @FXML
    private void onMasterDirectoryKeyTyped(KeyEvent event)
    {
        if (masterDirectoryName.getText() != null && !(tableFileExt.getText().isEmpty()))
        {
            WorkbenchOptions.MASTER_DIRECTORY = masterDirectoryName.getText();
        } else
        {
            WorkbenchOptions.MASTER_DIRECTORY = "sql_data";
        }
    }

    @FXML
    private void onSemiToggle(ActionEvent event)
    {
        if (emptyLineButton.isSelected()) emptyLineButton.setSelected(false);
        if (eachLineButton.isSelected()) eachLineButton.setSelected(false);

        WorkbenchOptions.COMMAND_DIVIDER = WorkbenchOptions.CommandDivider.SEMICOLON;

    }

    @FXML
    private void onEmptyLineToggle(ActionEvent event)
    {
        if (semiButton.isSelected()) semiButton.setSelected(false);
        if (eachLineButton.isSelected()) eachLineButton.setSelected(false);

        WorkbenchOptions.COMMAND_DIVIDER = WorkbenchOptions.CommandDivider.EMPTY_LINE;
    }

    @FXML
    private void onEachLineToggle(ActionEvent event)
    {
        if (emptyLineButton.isSelected()) emptyLineButton.setSelected(false);
        if (semiButton.isSelected()) semiButton.setSelected(false);

        WorkbenchOptions.COMMAND_DIVIDER = WorkbenchOptions.CommandDivider.EACH_LINE;

    }


}