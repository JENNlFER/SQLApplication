package jteissler.csci1302.sqlgui;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


/**
 * Created by Kyle on 3/15/2017.
 */
public class SQLPreferences {

        boolean execHighlightedBoolean = false;
        boolean printStatusHighlightedBoolean = false;
        boolean printErrorHighlightedBoolean = false;

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
        private void initialize() {

        }


        @FXML
        private void onExecHighlightedToggle(ActionEvent event) {
                if (execHighlightedBoolean) execHighlightedBoolean = false;
                else execHighlightedBoolean = true;
        }

        @FXML
        private void onPrintStatusToggle(ActionEvent event) {
                if (printStatusHighlightedBoolean) printStatusHighlightedBoolean = false;
                else printStatusHighlightedBoolean = true;
        }

        @FXML
        private void onPrintErrorToggle(ActionEvent event) {
                if (printErrorHighlightedBoolean) printErrorHighlightedBoolean = false;
                else printErrorHighlightedBoolean = true;
        }

        @FXML
        private void onTableKeyTyped(KeyEvent event) {
                if (tableFileExt.getText() != null && !(tableFileExt.getText().isEmpty())) {
                        WorkbenchOptions.TABLE_FILE_EXTENSION = tableFileExt.getText();
                }
                else
                        WorkbenchOptions.TABLE_FILE_EXTENSION = "tab";

        }

        @FXML
        private void onSavedKeyTyped(KeyEvent event) {
                if (savedFileExt.getText() != null && !(tableFileExt.getText().isEmpty())) {
                        WorkbenchOptions.SAVE_FILE_EXTENSION = savedFileExt.getText();
                }
                else
                        WorkbenchOptions.SAVE_FILE_EXTENSION = "sql";
        }

        @FXML
        private void onMasterDirectoryKeyTyped(KeyEvent event) {
                if (masterDirectoryName.getText() != null && !(tableFileExt.getText().isEmpty())) {
                        WorkbenchOptions.MASTER_DIRECTORY = masterDirectoryName.getText();
                }
                else
                        WorkbenchOptions.MASTER_DIRECTORY = "sql_data";
        }

        @FXML
        private void onSemiToggle(ActionEvent event) {
                if(emptyLineButton.isSelected()) emptyLineButton.setSelected(false);
                if(eachLineButton.isSelected()) eachLineButton.setSelected(false);

                WorkbenchOptions.COMMAND_DIVIDER = WorkbenchOptions.CommandDivider.SEMICOLON;

        }
        @FXML
        private void onEmptyLineToggle(ActionEvent event){
                if(semiButton.isSelected()) semiButton.setSelected(false);
                if(eachLineButton.isSelected()) eachLineButton.setSelected(false);

                WorkbenchOptions.COMMAND_DIVIDER = WorkbenchOptions.CommandDivider.EMPTY_LINE;
        }
        @FXML
        private void onEachLineToggle(ActionEvent event){
                if(emptyLineButton.isSelected()) emptyLineButton.setSelected(false);
                if(semiButton.isSelected()) semiButton.setSelected(false);

                WorkbenchOptions.COMMAND_DIVIDER = WorkbenchOptions.CommandDivider.EACH_LINE;

        }


}