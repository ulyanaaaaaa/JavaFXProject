package com.example.kursovoi2.client;

import com.example.kursovoi2.MainApplication;
import com.example.kursovoi2.client.hibernate.dao.ClientDaoWrapper;
import com.example.kursovoi2.client.hibernate.dao.WorkerDaoWrapper;
import com.example.kursovoi2.client.hibernate.dao.functional.ClientDao;
import com.example.kursovoi2.client.hibernate.dao.functional.RecordDao;
import com.example.kursovoi2.client.hibernate.dao.functional.WorkerDao;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class RecordViewController {
    @Getter
    @Setter
    private RecordDao referencedDao = null;

    @FXML
    private ComboBox<ClientDaoWrapper> client;
    @FXML
    private ComboBox<WorkerDaoWrapper> worker;
    @FXML
    private DatePicker datePicker;
    @FXML
    private JFXTimePicker timePicker;

    public void OnInitialise()
    {
        List<ClientDao> allClients = MainApplication.getControllerInstance().loadAll(ClientDao.class);
        List<WorkerDao> allWorkers = MainApplication.getControllerInstance().loadAll(WorkerDao.class);

        for (ClientDao _dao : allClients)
        {
            client.getItems().add(new ClientDaoWrapper(_dao));
        }
        for (WorkerDao _dao : allWorkers)
        {
            worker.getItems().add(new WorkerDaoWrapper(_dao));
        }

        if (referencedDao != null)
        {
            client.setValue(new ClientDaoWrapper(referencedDao.getClient()));
            worker.setValue(new WorkerDaoWrapper(referencedDao.getWorker()));
            datePicker.setValue(referencedDao.getDate().toLocalDate());
            timePicker.setValue(referencedDao.getTime().toLocalTime());
        }
    }

    @FXML
    public void Save(ActionEvent event)
    {
        if (referencedDao == null) referencedDao = new RecordDao();

        referencedDao.setClient(client.getValue().getReferenced());
        referencedDao.setWorker(worker.getValue().getReferenced());
        referencedDao.setDate(Date.valueOf(datePicker.getValue()));
        referencedDao.setTime(Time.valueOf(timePicker.getValue()));
        MainApplication.getControllerInstance().save(referencedDao);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void Delete(ActionEvent event)
    {
        if (referencedDao == null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Nothing to delete!");
            alert.show();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            return;
        }

        MainApplication.getControllerInstance().delete(referencedDao);

        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
