package com.example.kursovoi2.client;

import com.example.kursovoi2.MainApplication;
import com.example.kursovoi2.client.guiTools.modules.functional.AccountModule;
import com.example.kursovoi2.client.hibernate.dao.functional.AccountDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AdminController {
    @FXML
    TableView<AccountModule> tableView;

    @FXML
    private Button findButton;

    @FXML
    private Button showAllButton;

    private void BlockAccountDialog(AccountModule module) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Warning!", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Warning!");
        alert.setHeaderText("Are you sure to toggle block of " + module.getLogin().get());

        alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                if (alert.getResult() == ButtonType.OK) {
                    BlockAccount(module);
                }
            }
        });

        alert.show();
    }

    private void BlockAccount(AccountModule module) {
        module.getReferenced().setBlocked(!module.getReferenced().isBlocked());
        MainApplication.getControllerInstance().save(module.getReferenced());
        System.out.println("Toggled!");
        FetchAll();
    }

    public void FetchAll() {
        tableView.getItems().clear();

        List<AccountDao> daoList = MainApplication.getControllerInstance().loadAll(AccountDao.class);
        for (AccountDao dao : daoList) {
            AccountModule module = new AccountModule();
            module.convertFrom(dao);
            tableView.getItems().add(module);
        }
    }

    @FXML
    public void ShowAllAccounts() {
        FetchAll();
    }

    public void OnInitialise() {
        TableColumn<AccountModule, String> login = new TableColumn<>("Login");
        TableColumn<AccountModule, String> blocked = new TableColumn<>("Status");

        login.setCellValueFactory(c -> c.getValue().getLogin());

        blocked.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().isBlocked() ? "Blocked" : "Active"
        ));

        tableView.setRowFactory(tv -> {
            TableRow<AccountModule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    BlockAccountDialog(tableView.getSelectionModel().getSelectedItem());
                }
            });
            return row;
        });

        tableView.getColumns().addAll(login, blocked);

        FetchAll();
    }
    @FXML
    private void Help() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("admin/help.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.setTitle("Account Editor");
        stage.show();
    }

    @FXML
    private void StatsDialog() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("admin/stats.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.setTitle("Record Editor");
        stage.show();

        fxmlLoader.<AdminStatsController>getController().OnInitialise();
    }

    @FXML
    private void openFindAccountDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("admin/findAccountDialog.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Find Account");
            FindAccountDialogController controller = loader.getController();
            controller.setAdminController(this); // Передаем ссылку на AdminController
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveInFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Accounts");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (AccountModule module : tableView.getItems()) {
                    String line = module.getLogin().get() + "," + (module.isBlocked() ? "Blocked" : "Active");
                    writer.write(line);
                    writer.newLine();
                }
                // Optional: Show a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Accounts saved to file successfully!");
                alert.showAndWait();
            } catch (IOException e) {
                // Handle the exception (e.g., show an error message)
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to save accounts");
                alert.setContentText("An error occurred while saving the file: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
