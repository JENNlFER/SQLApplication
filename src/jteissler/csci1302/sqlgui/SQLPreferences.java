package jteissler.csci1302.sqlgui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Created by Kyle on 3/15/2017.
 */
public class SQLPreferences {

    public class SQLWorkbench
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
        private RadioButton endLineButton;

        @FXML
        private RadioButton colonButton;

        @FXML
        private void initialize()
        {

        }



    }



}
