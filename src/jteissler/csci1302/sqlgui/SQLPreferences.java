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
    private Preferences preferences;

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


    @FXML
    private void onExecHighlightedToggle(ActionEvent event)
    {
        WorkbenchOptions.setHighlightedCommands(execHighlighted.isSelected());
    }

    @FXML
    private void onPrintStatusToggle(ActionEvent event)
    {
        WorkbenchOptions.setStatusLog(sqlPrintStatus.isSelected());
    }

    @FXML
    private void onPrintErrorToggle(ActionEvent event)
    {
        WorkbenchOptions.setErrorLog(sqlPrintError.isSelected());
    }

    @FXML
    private void onTableKeyTyped(KeyEvent event)
    {
        if (tableFileExt.getText() != null)
        {
            WorkbenchOptions.setTableExtension(tableFileExt.getText());
        }
        else
        {
            WorkbenchOptions.setTableExtension("tab");
        }

    }

    @FXML
    private void onSavedKeyTyped(KeyEvent event)
    {
        if (savedFileExt.getText() != null)
        {
            WorkbenchOptions.setSaveExtension(savedFileExt.getText());
        }
        else
        {
            WorkbenchOptions.setSaveExtension("sql");
        }
    }

    @FXML
    private void onMasterDirectoryKeyTyped(KeyEvent event)
    {
        if (masterDirectoryName.getText() != null && !(tableFileExt.getText().isEmpty()))
        {
            WorkbenchOptions.setMasterFile(masterDirectoryName.getText());
        }
        else
        {
            WorkbenchOptions.setMasterFile("sql_data");
        }
    }

    @FXML
    private void onSemiToggle(ActionEvent event)
    {
        if (emptyLineButton.isSelected()) emptyLineButton.setSelected(false);
        if (eachLineButton.isSelected()) eachLineButton.setSelected(false);

        WorkbenchOptions.setCommandDivider(WorkbenchOptions.CommandDivider.SEMICOLON);

    }

    @FXML
    private void onEmptyLineToggle(ActionEvent event)
    {
        if (semiButton.isSelected()) semiButton.setSelected(false);
        if (eachLineButton.isSelected()) eachLineButton.setSelected(false);

        WorkbenchOptions.setCommandDivider(WorkbenchOptions.CommandDivider.EMPTY_LINE);
    }

    @FXML
    private void onEachLineToggle(ActionEvent event)
    {
        if (emptyLineButton.isSelected()) emptyLineButton.setSelected(false);
        if (semiButton.isSelected()) semiButton.setSelected(false);

        WorkbenchOptions.setCommandDivider(WorkbenchOptions.CommandDivider.EACH_LINE);

    }
}