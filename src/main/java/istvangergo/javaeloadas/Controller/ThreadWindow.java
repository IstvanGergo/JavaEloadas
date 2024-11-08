package istvangergo.javaeloadas.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThreadWindow {
    public VBox threadLayout;
    @FXML
    private Button firstButton;
    @FXML
    private Button secondButton;
    private Stage stage;
    @FXML
    private Label secondText;
    @FXML
    private Label thirdText;

    private ThreadClass thread2 = null;
    private ThreadClass thread3 = null;

    @FXML
    protected void onFirstButtonClick() {
        if(thread2 == null || !thread2.isAlive()){
            thread2 = new ThreadClass(1000, "First", secondText);
            thread2.start();
        }
    }
    @FXML
    protected void onSecondButtonClick() {
        if(thread3 == null || !thread3.isAlive()){
            thread3 = new ThreadClass(2000, "Second", thirdText);
            thread3.start();
        }
    }

    static class ThreadClass extends java.lang.Thread {
        private int sleepTime = 0;
        private String text = "";
        private final Label label;
        public ThreadClass(int _sleepTime, String _text, Label _label) {
            this.sleepTime = _sleepTime;
            this.text = _text;
            this.label = _label;
        }
        @FXML
        public void setText() {
            if (label.getText().equals(text)) {
                Platform.runLater(()->label.setText(""));
            } else {
                Platform.runLater(()->label.setText(text));
            }
        }
        @Override
        public void run() {
            while (true) {
                try {
                    setText();
                    java.lang.Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}