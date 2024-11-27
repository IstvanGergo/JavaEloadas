package istvangergo.javaeloadas.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
    }

    public void getAll() {
        setupTable(MNBtable,currencies);
        populateTable(MNBtable,data);
    }
    public void getFiltered(){
        String selectedCurrency = currenciesComboBox.getValue();
        LocalDate startDate = beginDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if (startDate.isAfter(endDate)) {
            return;
        }
        Map<LocalDate, Map<String, Float>> filteredData;
        if(currencySelection.isSelected()) {
        filteredData = data.entrySet().stream()
                .filter(entry-> entry.getKey().isEqual(startDate) || entry.getKey().isAfter(startDate) && entry.getKey().isBefore(endDate))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    Map<String, Float> filteredCurrencyMap = new HashMap<>();
                    filteredCurrencyMap.put(selectedCurrency, entry.getValue().get(selectedCurrency));
                    return filteredCurrencyMap;
                }));
        }
        else{
            filteredData = data.entrySet().stream()
                    .filter(entry-> entry.getKey().isEqual(startDate) || entry.getKey().isAfter(startDate) && entry.getKey().isBefore(endDate))
                    .filter(entry-> entry.getValue().containsKey(selectedCurrency))
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                        Map<String, Float> filteredCurrencyMap = new HashMap<>();
                        filteredCurrencyMap.put(selectedCurrency, entry.getValue().get(selectedCurrency));
                        return filteredCurrencyMap;
                    }));
        }
        setupTable(FilteredMNBtable,Set.of(selectedCurrency));
        populateTable(FilteredMNBtable, filteredData);

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
    public void populateTable(TableView<Map.Entry<LocalDate, Map<String, Float>>> table,
                              Map<LocalDate, Map<String, Float>> tableData) {
        ObservableList<Map.Entry<LocalDate, Map<String, Float>>> rows = FXCollections.observableArrayList(tableData.entrySet());
        rows.clear();
        rows.setAll(tableData.entrySet());
        table.setItems(rows);
    }
}
