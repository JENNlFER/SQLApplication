package jteissler.csci1302.sqlgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Created by Kyle on 3/15/2017.
 */
public class SQLPreferences {

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
        private void onExecHighlightedToggle(ActionEvent event){}

        @FXML
        private void onPrintStatusToggle(ActionEvent event){}

        @FXML
        private void onPrintErrorToggle(ActionEvent event){}

        @FXML
        private void onTableKeyTyped(ActionEvent event){}

        @FXML
        private void onSavedKeyTyped(ActionEvent event){}

        @FXML
        private void onMasterDirectoryKeyTyped(ActionEvent event){}

        @FXML
        private void onSemiToggle(ActionEvent event){}

        @FXML
        private void onEndLineToggle(ActionEvent event){}

        @FXML
        private void onColonToggle(ActionEvent event){}










}
