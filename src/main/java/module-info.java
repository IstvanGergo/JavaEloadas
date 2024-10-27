module istvangergo.javaeloadas {
    requires javafx.controls;
    requires javafx.fxml;


    opens istvangergo.javaeloadas to javafx.fxml;
    exports istvangergo.javaeloadas;
}