package com.example.kursovoi2;

import com.example.kursovoi2.API.NetworkController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

public class MainApplication extends Application {
    @Getter
    private static NetworkController controllerInstance;

    @Override
    public void start(Stage stage) throws IOException {
        org.burningwave.core.assembler.StaticComponentContainer.Modules.exportAllToAll();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        if (controllerInstance == null)
            controllerInstance = new NetworkController("127.0.0.1");
    }

    public static void main(String[] args) {
        launch();
    }
}