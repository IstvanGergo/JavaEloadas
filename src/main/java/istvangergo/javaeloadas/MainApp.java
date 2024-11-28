package istvangergo.javaeloadas;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @FXML
    private Button oandaButton;
    @FXML
    private Button soapClientButton;
    @FXML
    private Button threadHandlingButton;
    @FXML
    private Button crudAppButton;
    @FXML
    protected void OpenThreadHandling(){
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/ThreadWindow.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Thread Handling");
        stage.setScene(new Scene(root,300,200));
        stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void OpenCRUDApp(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View/CRUDAppWindow.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("CRUD App");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void OpenSOAPClient(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View/SOAPClientWindow.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("SOAP Client");
            stage.setScene(new Scene(root,800,600));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void OpenForexMenu(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View/OandaWindow.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Forex Menu");
            stage.setScene(new Scene(root,800,600));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("István Gergő");
        Parent root = FXMLLoader.load(getClass().getResource("View/main.fxml"));
        Scene mainScene = new Scene(root, 300, 200);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}