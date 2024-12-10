package com.example.kursovoi2.API;

import javafx.application.Application;
import javafx.stage.Stage;

public class ServerMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Server server = new Server();
        new Thread(server::start).start();
    }
}