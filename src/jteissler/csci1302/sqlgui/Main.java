package jteissler.csci1302.sqlgui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("resources/Workbench.fxml"));
        primaryStage.setTitle("SQL Workbench");
        Scene scene = new Scene(root, 800, 600);
        String css = getClass().getResource("resources/Workbench.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
