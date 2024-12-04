package com.example.kursovoi2.client;

import com.example.kursovoi2.MainApplication;
import com.example.kursovoi2.client.hibernate.dao.functional.AccountDao;
import com.example.kursovoi2.client.hibernate.dao.functional.ClientDao;
import com.example.kursovoi2.client.hibernate.dao.functional.WorkerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

public class EditAccount {
    @Getter
    @Setter
    private ClientDao client = null;
    @Getter
    @Setter
    private WorkerDao worker = null;

    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;

    public void OnInitialise() {
        phone.setDisable(worker != null);

        if (client != null)
        {
            login.setText(client.getAccount().getLogin());
            name.setText(client.getName());
            phone.setText(client.getPhone());
        }
        else {
            login.setText(worker.getAccount().getLogin());
            name.setText(worker.getName());
        }
    }

    private int validateFields() {
        if (name.getText().length() == 0 || (client != null && phone.getText().length() == 0)) return 2;
        if (password.getText().length() < 6 && password.getText().length() > 0) return 1;

        return 0;
    }

    @FXML
    public void Apply(ActionEvent event) {
        int code = validateFields();
        if (code != 0)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Warning!");
            switch (code)
            {
                case 1 -> alert.setHeaderText("Password is incorrect");
                case 2 -> alert.setHeaderText("Some fields are empty");
            }
            alert.show();
            return;
        }

        AccountDao _dao = (client != null) ? client.getAccount() : worker.getAccount();
        if (client != null) {
            client.setName(name.getText());
            client.setPhone(phone.getText());

            MainApplication.getControllerInstance().save(client);
        }
        else {
            worker.setName(name.getText());
            MainApplication.getControllerInstance().save(worker);
        }

        if (password.getText().length() > 0) _dao.setPassword(AccountDao.getPasswordHash(password.getText(), "1234"));

        MainApplication.getControllerInstance().save(_dao);

        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
