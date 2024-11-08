module istvangergo.javaeloadas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;


    opens istvangergo.javaeloadas to javafx.fxml;
    exports istvangergo.javaeloadas;
    exports istvangergo.javaeloadas.controllers;
    opens istvangergo.javaeloadas.controllers to javafx.fxml;
}