module istvangergo.javaeloadas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.sql;


    opens istvangergo.javaeloadas to javafx.fxml;
    opens istvangergo.javaeloadas.Model to javafx.fxml, javafx.base;
    opens istvangergo.javaeloadas.Controller to javafx.fxml;
    exports istvangergo.javaeloadas;
    exports istvangergo.javaeloadas.Controller;
}