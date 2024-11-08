package istvangergo.javaeloadas.DBHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUDApp {
    //TODO: Create CRUD functions
    private static final String URL = "jdbc:sqlitec:/adatok/adatok.db";
    public Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
