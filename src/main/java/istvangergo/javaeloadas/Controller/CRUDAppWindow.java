package istvangergo.javaeloadas.Controller;

import istvangergo.javaeloadas.DBHandler.CRUDApp;
import istvangergo.javaeloadas.Model.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CRUDAppWindow {
    private CRUDApp crudApp;
    /* getAll Table */
    @FXML
    private TableView<Animal> animalTableView;
    @FXML
    private TableColumn<Animal, Integer> idColumn;
    @FXML
    private TableColumn<Animal, String> nameColumn;
    @FXML
    private TableColumn<Animal, Integer> yearColumn;
    @FXML
    private TableColumn<Animal, String> categoryColumn;
    @FXML
    private TableColumn<Animal, Integer> valueColumn;

    /* getFiltered Table */
    @FXML
    private TableView<Animal> filteredTableView;
    @FXML
    private TableColumn<Animal, Integer> idColumnFiltered;
    @FXML
    private TableColumn<Animal, String> nameColumnFiltered;
    @FXML
    private TableColumn<Animal, Integer> yearColumnFiltered;
    @FXML
    private TableColumn<Animal, String> categoryColumnFiltered;
    @FXML
    private TableColumn<Animal, Integer> valueColumnFiltered;

    @FXML
    private TextField animalName;
    @FXML
    private RadioButton isYearAvailable;
    @FXML
    private CheckBox oneHundredThousand;
    @FXML
    private CheckBox twoHundredFiftyThousand;
    @FXML
    private CheckBox halfMillion;
    @FXML
    private CheckBox oneMillion;
    @FXML
    private List<CheckBox> valueCheckBoxes;
    @FXML
    private ComboBox animalCategory;


    public CRUDAppWindow(){
        crudApp = new CRUDApp();
    }

    private void tableSetter(TableColumn<Animal, Integer> idColumnFiltered,
                             TableColumn<Animal, String> nameColumnFiltered,
                             TableColumn<Animal, Integer> yearColumnFiltered,
                             TableColumn<Animal, String> categoryColumnFiltered,
                             TableColumn<Animal, Integer> valueColumnFiltered) {
        idColumnFiltered.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnFiltered.setCellValueFactory(new PropertyValueFactory<>("name"));
        yearColumnFiltered.setCellValueFactory(new PropertyValueFactory<>("year"));
        categoryColumnFiltered.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        valueColumnFiltered.setCellValueFactory(new PropertyValueFactory<>("Forint"));
    }
    public void initialize(){
        tableSetter(idColumn, nameColumn, yearColumn, categoryColumn, valueColumn);
        tableSetter(idColumnFiltered, nameColumnFiltered, yearColumnFiltered, categoryColumnFiltered, valueColumnFiltered);
        valueCheckBoxes = List.of(oneHundredThousand,twoHundredFiftyThousand,halfMillion,oneMillion);
    }

    @FXML
    protected void insert(){
        List<Animal> animalList =  crudApp.getAll();
        animalTableView.getItems().setAll(animalList);
    }
    @FXML
    protected void updateRecord(){
        List<Animal> animalList =  crudApp.getAll();
        animalTableView.getItems().setAll(animalList);
    }
    @FXML
    protected void deleteRecord(){
        List<Animal> animalList =  crudApp.getAll();
        animalTableView.getItems().setAll(animalList);
    }
    @FXML
    protected void getFiltered(){
        List<Integer> selectedValues = new ArrayList<>();
        for( CheckBox value : valueCheckBoxes){
            if(value.isSelected()){
                selectedValues.add(Integer.valueOf(value.getText()));

            }
        }
        List<Animal> animalList =  crudApp.getFiltered( animalName.getText(), isYearAvailable.selectedProperty(), selectedValues, (String) animalCategory.getValue());
        filteredTableView.getItems().setAll(animalList);
    }

    public void getAll(ActionEvent actionEvent) {
        List<Animal> animalList =  crudApp.getAll();
        animalTableView.getItems().setAll(animalList);
    }
}
