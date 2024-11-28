package istvangergo.javaeloadas.Controller;

import istvangergo.javaeloadas.DBHandler.CRUDApp;
import istvangergo.javaeloadas.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class CRUDAppWindow {
    private final CRUDApp crudApp;
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
    private ComboBox<String> animalCategory;
    @FXML
    private CheckBox oneHundredThousand;
    @FXML
    private CheckBox twoHundredFiftyThousand;
    @FXML
    private CheckBox halfMillion;
    @FXML
    private CheckBox oneMillion;
    private List<CheckBox> valueCheckBoxes;

    /* insert Table*/
    @FXML
    private TextField nameToInsert;
    @FXML
    private ComboBox<Value> valueToInsert;
    @FXML
    private TextField yearToInsert;
    @FXML
    private ComboBox<Category> categoryToInsert;

    /* modify Table */
    @FXML
    private ComboBox<Animal> animalIDs;
    @FXML
    private TextField modifiedName;
    @FXML
    private TextField modifiedYear;
    @FXML
    private ComboBox<Value>  modifiedValue;
    @FXML
    private ComboBox<Category>  modifiedCategory;

    /* delete Table*/
    @FXML
    private ComboBox<Animal> deleteIDs;

    public CRUDAppWindow(){
        crudApp = new CRUDApp();
    }

    private void populateValueComboBox() {
        List<Value> values = crudApp.getValues();
        valueToInsert.getItems().addAll(values);
        modifiedValue.getItems().addAll(values);
    }

    private void populateCategoryComboBox() {
        List<Category> categories = crudApp.getCategories();
        categoryToInsert.getItems().addAll(categories);
        modifiedCategory.getItems().addAll(categories);
    }
    private void populateAnimalIDs(){
        ObservableList<Animal> animals = FXCollections.observableArrayList();
        animals.clear();
        animals = crudApp.getAll();
        animalIDs.setItems(animals);
        deleteIDs.setItems(animals);
    }

    private void tableSetter(TableColumn<Animal, Integer> _idColumn,
                             TableColumn<Animal, String> _nameColumn,
                             TableColumn<Animal, Integer> _yearColumn,
                             TableColumn<Animal, String> _categoryColumn,
                             TableColumn<Animal, Integer> _valueColumn) {
        _idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        _nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        _yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        _categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        _valueColumn.setCellValueFactory(new PropertyValueFactory<>("Forint"));
    }
    public void initialize(){
        tableSetter(idColumn, nameColumn, yearColumn, categoryColumn, valueColumn);
        tableSetter(idColumnFiltered, nameColumnFiltered, yearColumnFiltered, categoryColumnFiltered, valueColumnFiltered);
        valueCheckBoxes = List.of(oneHundredThousand, twoHundredFiftyThousand, halfMillion, oneMillion);
        populateValueComboBox();
        populateCategoryComboBox();
        populateAnimalIDs();
    }
    @FXML
    protected void insert(){
       Value selectedValue = valueToInsert.getValue();
       Category selectedCategory = categoryToInsert.getValue();
       int year;
       if(yearToInsert.getText().isEmpty()){
           year = 0;
       }
       else{
           year = Integer.parseInt(yearToInsert.getText());
       }
       boolean success = crudApp.insert(nameToInsert.getText(), selectedValue.getId(), year, selectedCategory.getId());
        if (success) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            populateAnimalIDs();
            successAlert.setTitle("Sikeres írás");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Állat sikeresen hozzáadva!");
            successAlert.showAndWait();
        } else {
            Alert failureAlert = new Alert(Alert.AlertType.WARNING);
            failureAlert.setTitle("Sikertelen írás");
            failureAlert.setHeaderText(null);
            failureAlert.setContentText("Már létezik ilyen nevű állat az adatbázisban!");
            failureAlert.showAndWait();
        }
    }
    @FXML
    protected void updateRecord(){
        Value selectedValue = modifiedValue.getValue();
        Category selectedCategory = modifiedCategory.getValue();
        Animal selectedAnimal = animalIDs.getValue();
        boolean success = crudApp.modify(selectedAnimal.getId(), modifiedName.getText(), selectedValue, modifiedYear.getText(), selectedCategory);
        if (success) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Sikeres módosítás");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Állat sikeresen módosítva!");
            successAlert.showAndWait();
        } else {
            Alert failureAlert = new Alert(Alert.AlertType.WARNING);
            failureAlert.setTitle("Sikertelen módosítás");
            failureAlert.setHeaderText(null);
            failureAlert.setContentText("Ilyen névvel létezik már állat!");
            failureAlert.showAndWait();
        }
    }
    @FXML
    protected void deleteRecord(){
        Animal selectedAnimal = deleteIDs.getValue();
        Animal deletedAnimal = crudApp.delete(selectedAnimal.getId());
        if(deletedAnimal != null){
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            populateAnimalIDs();
            successAlert.setTitle("Sikeres törlés");
            successAlert.setHeaderText(null);
            successAlert.setContentText(deletedAnimal.getName() + " nevű állat sikeresen törölve");
            successAlert.showAndWait();
            deleteIDs.getItems().remove(deletedAnimal);
        }
        else{
            Alert failureAlert = new Alert(Alert.AlertType.WARNING);
            failureAlert.setTitle("Sikertelen törlés");
            failureAlert.setHeaderText(null);
            failureAlert.setContentText("Törlés nem sikerült!");
            failureAlert.showAndWait();
        }
    }

    @FXML
    protected void getFiltered(){
        List<Integer> selectedValues = new ArrayList<>();
        for(CheckBox value : valueCheckBoxes){
            if(value.isSelected()){
                selectedValues.add(Integer.valueOf(value.getText()));
            }
        }
        List<Animal> animalList =  crudApp.getFiltered( animalName.getText(),
                isYearAvailable.selectedProperty(), selectedValues, animalCategory.getValue());
        filteredTableView.getItems().setAll(animalList);
    }

    public void getAll() {
        ObservableList<Animal> animalList =  crudApp.getAll();
        animalTableView.setItems(animalList);
    }
}
