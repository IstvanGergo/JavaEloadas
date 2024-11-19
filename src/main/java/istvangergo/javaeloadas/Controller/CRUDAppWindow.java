package istvangergo.javaeloadas.Controller;

import istvangergo.javaeloadas.DBHandler.CRUDApp;
import istvangergo.javaeloadas.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private ComboBox animalCategory;
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

    /* insert Table*/
    @FXML
    private TextField nameToInsert;
    @FXML
    private ComboBox valueToInsert;
    @FXML
    private TextField yearToInsert;
    @FXML
    private ComboBox categoryToInsert;

    /* modify Table */
    @FXML
    private ComboBox animalIDs;
    @FXML
    private TextField modifiedName;
    @FXML
    private TextField modifiedYear;
    @FXML
    private ComboBox  modifiedValue;
    @FXML
    private ComboBox  modifiedCategory;

    /* delete Table*/
    @FXML
    private ComboBox deleteIDs;

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
        List<Animal> animals = crudApp.getAll();
        animalIDs.getItems().addAll(animals);
        deleteIDs.getItems().addAll(animals);
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
        valueCheckBoxes = List.of(oneHundredThousand, twoHundredFiftyThousand, halfMillion, oneMillion);
        populateValueComboBox();
        populateCategoryComboBox();
        populateAnimalIDs();
    }

    @FXML
    protected void insert(){
       Value selectedValue = (Value) valueToInsert.getValue();
       Category selectedCategory = (Category) categoryToInsert.getValue();
       boolean success = crudApp.insert(nameToInsert.getText(), selectedValue.getId(), Integer.parseInt(yearToInsert.getText()), selectedCategory.getId());
        if (success) {
            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Sikeres írás");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Állat sikeresen hozzáadva!");
            successAlert.showAndWait();
        } else {
            // Show failure message
            Alert failureAlert = new Alert(Alert.AlertType.WARNING);
            failureAlert.setTitle("Sikertelen írás");
            failureAlert.setHeaderText(null);
            failureAlert.setContentText("Már létezik ilyen nevű állat az adatbázisban!");
            failureAlert.showAndWait();
        }
    }
    @FXML
    protected void updateRecord(){
        Value selectedValue = (Value) modifiedValue.getValue();
        Category selectedCategory = (Category) modifiedCategory.getValue();
        Animal selectedAnimal = (Animal) animalIDs.getValue();
        boolean success = crudApp.modify(selectedAnimal.getId(),modifiedName.getText(),selectedValue.getId(),Integer.parseInt(modifiedYear.getText()),selectedCategory.getId());
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
        Animal selectedAnimal = (Animal) deleteIDs.getValue();
        Animal deletedAnimal = crudApp.delete(selectedAnimal.getId());
        if(deletedAnimal != null){
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
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
