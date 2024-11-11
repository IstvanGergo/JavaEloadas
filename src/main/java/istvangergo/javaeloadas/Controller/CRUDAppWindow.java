package istvangergo.javaeloadas.Controller;

import istvangergo.javaeloadas.DBHandler.CRUDApp;
import istvangergo.javaeloadas.Model.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CRUDAppWindow {
    private CRUDApp crudApp;
    public CRUDAppWindow(){
        crudApp = new CRUDApp();
    }
    @FXML
    protected void getAll(){
        List<Animal> animalList =  crudApp.getAll();
        animalList.forEach(animal -> System.out.println(animal));

    }
}
