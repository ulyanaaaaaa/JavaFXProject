package com.example.kursovoi2.API;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConnectionStatsWindow {
    private Stage stage;
    private TextArea textArea;

    public ConnectionStatsWindow() {
        stage = new Stage();
        stage.setTitle("Статистика подключений");

        textArea = new TextArea();
        textArea.setEditable(false);

        VBox vbox = new VBox(textArea);
        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    public void updateStats(String message) {
        Platform.runLater(() -> textArea.appendText(message + "\n"));
    }
}