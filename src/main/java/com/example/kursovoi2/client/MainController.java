package com.example.kursovoi2.client;

import com.example.kursovoi2.MainApplication;
import com.example.kursovoi2.client.guiTools.modules.functional.RecordModule;
import com.example.kursovoi2.client.hibernate.dao.dao;
import com.example.kursovoi2.client.hibernate.dao.functional.ClientDao;
import com.example.kursovoi2.client.hibernate.dao.functional.RecordDao;
import com.example.kursovoi2.client.hibernate.dao.functional.WorkerDao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class MainController {
    @Getter
    @Setter
    private static ClientDao client = null;

    @Getter
    @Setter
    private static WorkerDao worker = null;

    @FXML
    private TableView<RecordModule> tableView;
    @FXML
    private MenuItem addBtn;
    @FXML
    private DatePicker datePicker;

    private final TableColumn<RecordModule, String> clientName = new TableColumn<>("Client");
    private final TableColumn<RecordModule, String> clientMobile = new TableColumn<>("Mobile");
    private final TableColumn<RecordModule, String> workerName = new TableColumn<>("Worker");
    private final TableColumn<RecordModule, Date> date = new TableColumn<>("Date");
    private final TableColumn<RecordModule, Time> time = new TableColumn<>("Time");

    public void OnInitialise() {
        addBtn.setDisable(worker == null);

        clientName.setCellValueFactory(c -> c.getValue().getClient().getName());
        clientMobile.setCellValueFactory(c -> c.getValue().getClient().getPhone());
        workerName.setCellValueFactory(c -> c.getValue().getWorker().getName());
        date.setCellValueFactory(c -> c.getValue().getDate());
        time.setCellValueFactory(c -> c.getValue().getTime());

        tableView.getColumns().addAll(clientName, clientMobile, workerName, date, time);

        tableView.setRowFactory( tv -> {
            TableRow<RecordModule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("worker/record-control.fxml"));
                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setScene(scene);
                    stage.setTitle("Record Editor");
                    stage.show();

                    fxmlLoader.<RecordViewController>getController().setReferencedDao(row.getItem().getDao());
                    fxmlLoader.<RecordViewController>getController().OnInitialise();
                }
            });
            return row;
        });
        FetchAll();
    }

    @FXML
    public void OpenEditRecordDialog() {
        // Add here TableView support

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("worker/record-control.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.setTitle("Record Editor");
        stage.show();
        fxmlLoader.<RecordViewController>getController().OnInitialise();
    }

    @FXML
    private void FetchAll() {
        tableView.getItems().clear();
        List<RecordDao> daoList = null;
        if (worker != null)
            daoList = MainApplication.getControllerInstance().loadAll(RecordDao.class);
        else if (client != null)
            daoList = MainApplication.getControllerInstance().loadByCriteria(RecordDao.class, "client", client);

        for (RecordDao dao : daoList)
        {
            RecordModule module = new RecordModule();
            module.convertFrom(dao);
            tableView.getItems().add(module);
        }
    }

    @FXML
    private void FetchAllByDatePickerFilter() {
        tableView.getItems().clear();
        Date date = Date.valueOf(datePicker.getValue());

        List <RecordDao> daoList = null;
        if (worker != null)
            daoList = MainApplication.getControllerInstance().loadByCriteria(RecordDao.class, "date", date);
        else if (client != null) {
            daoList = MainApplication.getControllerInstance().loadByCriteria(RecordDao.class, "date", date);
            daoList.removeIf(dao -> dao.getClient() != client);
        }

        for (RecordDao dao : daoList)
        {
            RecordModule module = new RecordModule();
            module.convertFrom(dao);
            tableView.getItems().add(module);
        }
    }

    @FXML
    private void EditAccountDialog()
    {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("client/edit-account.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.setTitle("Account Editor");
        stage.show();

        fxmlLoader.<EditAccount>getController().setClient(client);
        fxmlLoader.<EditAccount>getController().setWorker(worker);
        fxmlLoader.<EditAccount>getController().OnInitialise();
    }

    @FXML
    private void Help()
    {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("client/help.fxml"));
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
    private void DeleteAccountDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure?", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Warning!");
        alert.setHeaderText("Are you sure?");

        alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent _event) {
                if (alert.getResult() == ButtonType.OK)
                {
                    Delete();
                    javafx.application.Platform.exit();
                }
            }
        });

        alert.show();
    }

    private void Delete()
    {
        List<RecordDao> listToCleanup;

        if (client != null)
        {
            listToCleanup = MainApplication.getControllerInstance().loadByCriteria(RecordDao.class, "client", client);
        }
        else
        {
            listToCleanup = MainApplication.getControllerInstance().loadByCriteria(RecordDao.class, "worker", worker);
        }

        for (RecordDao toDelete : listToCleanup)
            MainApplication.getControllerInstance().delete(toDelete);
        MainApplication.getControllerInstance().delete((client != null) ? client : worker);
        MainApplication.getControllerInstance().delete((client != null) ? client.getAccount() : worker.getAccount());
    }


}
