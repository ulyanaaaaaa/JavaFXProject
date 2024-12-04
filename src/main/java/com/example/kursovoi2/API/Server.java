package com.example.kursovoi2.API;

import com.example.kursovoi2.client.hibernate.TransactionController;
import lombok.Getter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Platform;

public class Server {
    @Getter
    private ServerSocket server;
    @Getter
    private static TransactionController controller;
    private int connectionCount = 0;
    private ConnectionStatsWindow statsWindow;

    public void start() {
        try {
            server = new ServerSocket(1234);
            server.setReuseAddress(true);
            System.out.println("Сервер запущен на порту 1234");
            System.out.println("Сервер готов к подключениям...");

            // Создание окна статистики в потоке JavaFX
            Platform.runLater(() -> {
                statsWindow = new ConnectionStatsWindow();
                statsWindow.show();
            });

            if (controller == null)
                controller = new TransactionController();

            while (true) {
                Socket socket = server.accept();
                connectionCount++;
                String message = "Клиент подключен. Общее количество подключений: " + connectionCount;
                System.out.println(message);

                // Обновление окна статистики
                Platform.runLater(() -> {
                    System.out.println("Обновление статистики: " + message); // Лог для отладки
                    statsWindow.updateStats(message);
                });

                ClientConnection connection = new ClientConnection(socket);
                new Thread(connection).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}