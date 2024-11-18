package istvangergo.javaeloadas.DBHandler;

import istvangergo.javaeloadas.Model.*;
import javafx.beans.property.BooleanProperty;

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

    private List<Animal> getTableContents(ResultSet resultSet) throws SQLException {
        List<Animal> animals = new ArrayList<>();
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

    public List<Animal> getAll() {
        try {
            connect();
            ResultSet resultSet = statement.executeQuery("Select Animal.ID, Animal.Name, Year, Category.CategoryID, CategoryName, Value.ValueID, Value.Forint\n" +
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
            StringBuilder query = new StringBuilder("Select Animal.ID, Animal.Name, Year, Category.CategoryID, CategoryName, Value.ValueID, Value.Forint\n" +
                    "from Animal\n" +
                    "JOIN Category on Animal.CategoryID = Category.CategoryID\n" +
                    "JOIN Value on Animal.ValueID = Value.ValueID\n" +
                    "where 1=1 ");
            if(!_animalNameContains.isBlank()){
                query.append(" AND Animal.Name LIKE '%").append(_animalNameContains).append("%'");
            }
            if(_values != null && !_values.isEmpty()){
                query.append(" AND Value.Forint = ").append(_values.get(0));
                for (Integer value : _values) {
                        query.append(" OR Value.Forint = ").append(value);
                    }
            }
            if(_category != null && !_category.equals("Ne szűrjön")){
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
                Value value = new Value(rs.getInt("ValueID"), rs.getInt("Forint")); // Assuming 'name' is a column
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
                Category category = new Category(rs.getInt("CategoryID"), rs.getString("CategoryName")); // Assuming 'name' is a column
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
}
