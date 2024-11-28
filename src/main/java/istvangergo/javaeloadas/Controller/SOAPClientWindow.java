package istvangergo.javaeloadas.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static istvangergo.javaeloadas.Controller.XMLParser.parseAndTransform;

public class SOAPClientWindow {
    @FXML
    TableView<Map.Entry<LocalDate, Map<String, Float>>> MNBtable;
    @FXML
    TableView<Map.Entry<LocalDate, Map<String, Float>>> FilteredMNBtable;
    @FXML
    ComboBox<String> currenciesComboBox;
    @FXML
    DatePicker beginDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    RadioButton currencySelection;
    @FXML
    GridPane currencyGrid;
    @FXML
    RadioButton detailedSelection;

    List<CheckBox> deviceCheckBoxes;

    /* Containers for initialization */
    Set<String> currencies = new HashSet<>();
    Map<LocalDate, Map<String, Float>> data;
    public void initialize() throws Exception{
        beginDatePicker.setValue(LocalDate.now().minusMonths(1));
        endDatePicker.setValue(LocalDate.now());
        data = parseAndTransform(new File("C:/adatok/MNB.txt"));
        currencies = data.values().stream()
                .flatMap(map -> map.keySet().stream())
                .collect(Collectors.toSet());
        currenciesComboBox.setItems(FXCollections.observableArrayList(currencies));
        fillCurrencyGrid();
    }

    public void getAll() {
        MNBtable.getColumns().clear();
        setupTable(MNBtable,currencies);
        ObservableList<Map.Entry<LocalDate, Map<String,Float>>> rowList = FXCollections.observableArrayList(data.entrySet());
        MNBtable.setItems(rowList);
    }

    public void getFiltered(){
        String selectedCurrency = currenciesComboBox.getValue();
        Set<String> selectedCurrencies = new HashSet<>();
        LocalDate startDate = beginDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if (startDate.isAfter(endDate)) {
            return;
        }
        Map<LocalDate, Map<String, Float>> filteredData;
        filteredData = data.entrySet().stream()
                .filter(entry -> entry.getKey().isEqual(startDate) ||
                        (entry.getKey().isAfter(startDate) && entry.getKey().isBefore(endDate)))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    Map<String, Float> filteredCurrencyMap = new HashMap<>();
                    if (currencySelection.isSelected()) {
                        filteredCurrencyMap.put(selectedCurrency, entry.getValue().get(selectedCurrency));
                    } else if(detailedSelection.isSelected()) {
                        for (CheckBox box : deviceCheckBoxes) {
                            if (box.isSelected()) {
                                selectedCurrencies.add(box.getText());
                                filteredCurrencyMap.put(box.getText(), entry.getValue().get(box.getText()));
                            }
                        }
                    } else {
                        filteredCurrencyMap.putAll(entry.getValue());
                    }
                    return filteredCurrencyMap; }));
        FilteredMNBtable.getColumns().clear();
        if(currencySelection.isSelected()) {
            setupTable(FilteredMNBtable,Set.of(selectedCurrency));
        }
        else if(detailedSelection.isSelected()) {
            setupTable(FilteredMNBtable, selectedCurrencies);
        }
        else{
            setupTable(FilteredMNBtable,currencies);
        }
        ObservableList<Map.Entry<LocalDate, Map<String,Float>>> rowList = FXCollections.observableArrayList(filteredData.entrySet());
        FilteredMNBtable.setItems(rowList);
    }

    public void setupTable( TableView<Map.Entry<LocalDate, Map<String, Float>>> table,
                            Set<String> currencies) {
        TableColumn<Map.Entry<LocalDate, Map<String, Float>>, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getKey().toString())
        );
        table.getColumns().add(dateColumn);
        for (String currency : currencies) {
            TableColumn<Map.Entry<LocalDate, Map<String, Float>>, String> currencyColumn = new TableColumn<>(currency);
            currencyColumn.setCellValueFactory(cellData -> {
                Float value = cellData.getValue().getValue().get(currency);
                return new SimpleStringProperty(value != null ? value.toString() : "");
            });
            table.getColumns().add(currencyColumn);
        }
    }

    public void currencyPick() {
        if(detailedSelection.isSelected()){
            currencyGrid.setVisible(true);
        }
        else{
            currencyGrid.setVisible(false);
        }
    }
    public void fillCurrencyGrid() {
        deviceCheckBoxes = new ArrayList<>();
        int columns = 6;
        int row = 0;
        int col = 0;
        for (String currency : currencies) {
            CheckBox box = new CheckBox(currency);
            box.setSelected(false);
            deviceCheckBoxes.add(box);
            currencyGrid.add(box, col, row);
            col++;
            if (col == columns) {
                col = 0;
                row++;
            }
        }
    }
}
