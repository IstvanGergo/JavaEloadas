package istvangergo.javaeloadas.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
    @FXML
    private Button threadHandlingButton;

    @FXML
    private Button crudAppButton;

    @FXML
    public void initialize() {
        // Event handling to switch scenes
        threadHandlingButton.setOnAction(e -> {
            ThreadWindow threadHandling = new ThreadWindow();
        });

        crudAppButton.setOnAction(e -> {
            CRUDAppWindow crudApp = new CRUDAppWindow();
            crudApp.show();
       });
    }
}
