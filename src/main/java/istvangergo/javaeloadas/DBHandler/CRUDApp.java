package istvangergo.javaeloadas.DBHandler;

import istvangergo.javaeloadas.Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUDApp {
    //TODO: Create CRUD functions
    private static final String URL = "jdbc:sqlite:c:/adatok/adatok.db";
    Connection connection;
    Statement statement;
    public void connect() throws SQLException {
        connection = DriverManager.getConnection(URL);
        statement = connection.createStatement();
    }

    public List<Animal> getAll() {
        try {
            connect();
            ResultSet resultSet = statement.executeQuery("Select Animal.ID, Animal.Name, Year, Category.CategoryID, CategoryName, Value.ValueID, Value.Forint\n" +
                    "from Animal\n" +
                    "JOIN Category on Animal.CategoryID = Category.CategoryID\n" +
                    "JOIN Value on Animal.ValueID = Value.ValueID");
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public boolean insert(Animal animal) {
        try {
            connect();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Animal( Name, ValueID, Year, CategoryID) VALUES (?,?,?,?)");
            preparedStatement.setString(1, animal.getName());
            preparedStatement.setInt(2, animal.getValue());
            preparedStatement.setInt(3, animal.getYear());
            preparedStatement.setInt(4, animal.getCategory());
            int rows = preparedStatement.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
