package istvangergo.javaeloadas.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CRUDAppWindow {
    private VBox crudLayout;
    private Stage stage;
    private ObservableList<String> items;
    private ListView<String> listView;

    public CRUDAppWindow() {
        stage = new Stage();
        stage.setTitle("CRUD App");
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CRUDAppWindow.fxml"));
            loader.setController(this);
            crudLayout = loader.load();
            stage.setScene(new Scene(crudLayout, 300, 300));
            items = FXCollections.observableArrayList();
            listView = new ListView<>(items);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void show() {
        stage.show();
    }
}
