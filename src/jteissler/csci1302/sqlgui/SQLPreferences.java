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
        private RadioButton endLineButton;

        @FXML
        private RadioButton colonButton;

        @FXML
        private void initialize()
        {

        }


        @FXML
        private void onExecHighlightedToggle(ActionEvent event){
             if(execHighlightedBoolean) execHighlightedBoolean = false;
             else execHighlightedBoolean = true;
        }

        @FXML
        private void onPrintStatusToggle(ActionEvent event){
                if(printStatusHighlightedBoolean) printStatusHighlightedBoolean = false;
                else printStatusHighlightedBoolean = true;
        }

        @FXML
        private void onPrintErrorToggle(ActionEvent event){
                if(printErrorHighlightedBoolean) printErrorHighlightedBoolean = false;
                else printErrorHighlightedBoolean = true;
        }

        @FXML
        private void onTableKeyTyped(KeyEvent event) {
                if (tableFileExt.getText() != null) {
                        WorkbenchOptions.TABLE_FILE_EXTENSION = tableFileExt.getText();
                }
        }
        @FXML
        private void onSavedKeyTyped(ActionEvent event) {
                if (savedFileExt.getText() != null) {
                        WorkbenchOptions.SAVE_FILE_EXTENSION = savedFileExt.getText();
                }
        }
        @FXML
        private void onMasterDirectoryKeyTyped(ActionEvent event){
                if(masterDirectoryName.getText() != null) {
                        WorkbenchOptions.MASTER_DIRECTORY = masterDirectoryName.getText();
                }
        }

        @FXML
        private void onSemiToggle(ActionEvent event){}

        @FXML
        private void onEndLineToggle(ActionEvent event){}

        @FXML
        private void onColonToggle(ActionEvent event){}

}
