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

        ListView<String> list = (ListView) scene.lookup("#status-output");
        ObservableList<String> items =  FXCollections.observableArrayList (
                "Status 1", "Status 2", "Status 3", "Status 4", "Status 5");
        list.setItems(items);
        list.scrollTo(items.size() - 1);

        ListView<String> list2 = (ListView) scene.lookup("#error-output");
        ObservableList<String> items2 =  FXCollections.observableArrayList (
                "Error 1", "Error 2", "Error 3", "Error 4", "Error 5");
        list2.setItems(items2);
        list2.scrollTo(items2.size() - 1);

        TreeView<String> view = (TreeView) scene.lookup("#directory-tree");
        TreeItem<String> rootnode = new TreeItem<>("SQL Workbench");
        rootnode.setExpanded(true);

        TreeItem item = new TreeItem<>("Database 2");
        item.getChildren().add(new TreeItem<>("Table 1"));
        rootnode.getChildren().addAll(
                new TreeItem<>("Database 1"),
                item ,
                new TreeItem<>("Database 3")
        );
        view.setRoot(rootnode);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
