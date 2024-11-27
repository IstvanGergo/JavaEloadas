package istvangergo.javaeloadas.DBHandler;

import istvangergo.javaeloadas.Model.*;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUDApp {
    private static final String URL = "jdbc:sqlite:c:/adatok/adatok.db";
    Connection connection;
    Statement statement;
    public void connect() throws SQLException {

        connection = DriverManager.getConnection(URL);
        statement = connection.createStatement();
    }

    private ObservableList<Animal> getTableContents(ResultSet resultSet) throws SQLException {
        ObservableList<Animal> animals = FXCollections.observableArrayList();
        Map<Integer, Category> categoryMap = new HashMap<>();
        Map<Integer, Value> valueMap = new HashMap<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("Name");
            int Year = resultSet.getInt("Year");

            int categoryID = resultSet.getInt("CategoryID");
            String categoryName = resultSet.getString("CategoryName");
            Category category = categoryMap.computeIfAbsent(categoryID, k -> new Category(categoryID, categoryName));
            int valueID = resultSet.getInt("ValueID");
            int forint = resultSet.getInt("Forint");
            Value value = valueMap.computeIfAbsent(valueID, k -> new Value(valueID, forint));
            animals.add(new Animal(id, name, Year, value, category ));
        }
        return animals;
    }

    public ObservableList<Animal> getAll() {
        try {
            connect();
            ResultSet resultSet = statement.executeQuery(
                    "Select Animal.ID, Animal.Name, Year, Category.CategoryID, CategoryName, Value.ValueID, Value.Forint\n" +
                    "from Animal\n" +
                    "JOIN Category on Animal.CategoryID = Category.CategoryID\n" +
                    "JOIN Value on Animal.ValueID = Value.ValueID");
            return getTableContents(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Animal> getFiltered(String _animalNameContains, BooleanProperty _isYearAvailable, List<Integer> _values, String _category ){
        try{
            connect();
            StringBuilder query = new StringBuilder(
                    "Select Animal.ID, Animal.Name, Year, Category.CategoryID, CategoryName, Value.ValueID, Value.Forint\n" +
                    "from Animal\n" +
                    "JOIN Category on Animal.CategoryID = Category.CategoryID\n" +
                    "JOIN Value on Animal.ValueID = Value.ValueID\n" +
                    "where 1=1 ");
            if(!_animalNameContains.isBlank()){
                query.append(" AND Animal.Name LIKE '%").append(_animalNameContains).append("%'");
            }
            if(_values != null && !_values.isEmpty()){
                query.append(" AND Value.Forint = ").append(_values.get(0));
                _values.remove(0);
                for (Integer value : _values) {
                        query.append(" OR Value.Forint = ").append(value);
                    }
            }
            if(_category != null && !_category.isEmpty()){
                query.append(" AND Category.CategoryName LIKE '%").append(_category).append("%'");
            }
            if(_isYearAvailable.getValue()){
                query.append(" AND Year IS NOT NULL");
            }
            ResultSet resultSet = statement.executeQuery(query.toString());
            return getTableContents(resultSet);
        } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
        return null;
    }

    public List<Value> getValues() {
        try {
            connect();
            List<Value> values = new ArrayList<>();
            ResultSet rs = statement.executeQuery("SELECT * FROM Value");
            while (rs.next()) {
                Value value = new Value(rs.getInt("ValueID"), rs.getInt("Forint"));
                values.add(value);
            }
            return values;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> getCategories() {
        try {
            connect();
            List<Category> categories = new ArrayList<>();
            ResultSet rs = statement.executeQuery("SELECT * FROM Category");
            while (rs.next()) {
                Category category = new Category(rs.getInt("CategoryID"), rs.getString("CategoryName"));
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(String _name, Integer _valueID, Integer _year, Integer _categoryID) {
        try {
            connect();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Animal( Name, ValueID, Year, CategoryID) VALUES (?,?,?,?)");
            preparedStatement.setString(1, _name);
            preparedStatement.setInt(2, _valueID);
            preparedStatement.setInt(3, _year);
            preparedStatement.setInt(4, _categoryID);
            int rows = preparedStatement.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean modify(Integer _id, String _name, Value _value, String _year, Category _category) {
        try {
            connect();
            StringBuilder query = new StringBuilder("UPDATE Animal SET ");
            if(_name != null && !_name.isEmpty()){
                query.append(" Name = \"").append(_name).append("\"").append(",");
            }
            if(_value != null){
                query.append(" ValueID =").append(_value.getId()).append(",");
            }
            if(!_year.isEmpty()){
                query.append(" Year =").append(_year).append(",");
            }
            if(_category != null){
                query.append(" CategoryID =").append(_category.getId()).append(",");
            }
            query.delete(query.length()-1, query.length());
            query.append(" WHERE ID = ").append(_id);
            int rows = statement.executeUpdate(query.toString());
            return rows == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public Animal delete(Integer _id) {
        try{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "Select Animal.ID, Animal.Name, Year, Category.CategoryID, CategoryName, Value.ValueID, Value.Forint\n" +
                "from Animal\n" +
                "JOIN Category on Animal.CategoryID = Category.CategoryID\n" +
                "JOIN Value on Animal.ValueID = Value.ValueID\n" +
                "where Animal.ID = ? ");
        preparedStatement.setInt(1, _id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Animal> animal = getTableContents(resultSet);
        if(!animal.isEmpty()){
            PreparedStatement deletionStatement = connection.prepareStatement("DELETE FROM Animal WHERE ID = ?");
            deletionStatement.setInt(1, _id);
            deletionStatement.executeUpdate();
            return animal.get(0);
        }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
