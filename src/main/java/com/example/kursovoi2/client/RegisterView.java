package com.example.kursovoi2.client;

import com.example.kursovoi2.MainApplication;
import com.example.kursovoi2.client.hibernate.dao.dao;
import com.example.kursovoi2.client.hibernate.dao.functional.AccountDao;
import com.example.kursovoi2.client.hibernate.dao.functional.AdminDao;
import com.example.kursovoi2.client.hibernate.dao.functional.ClientDao;
import com.example.kursovoi2.client.hibernate.dao.functional.WorkerDao;
import com.example.kursovoi2.client.internal.AccountType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.HibernateException;

public class RegisterView {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<AccountType> accountType;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;

    public void OnInitialise() {
        accountType.getItems().addAll(AccountType.CLIENT, AccountType.WORKER, AccountType.ADMIN);

        accountType.setOnAction(event -> {
            phone.setDisable(accountType.getValue().equals(AccountType.WORKER) || accountType.getValue().equals(AccountType.ADMIN));
            name.setDisable(accountType.getValue().equals(AccountType.ADMIN));
        });
    }

    @FXML
    private void CreateAccount(ActionEvent event)
    {
        if (accountType.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error! Account type is not set!", ButtonType.CLOSE);
            alert.setTitle("Registration error");
            alert.show();
            return;
        }
        if (login.getText() == null || password.getText().length() < 6)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect data input");
            alert.setTitle("Registration error");
            alert.show();
            return;
        }

        String _login = login.getText();
        String _password = AccountDao.getPasswordHash(password.getText(), "1234");

        AccountDao _dao = new AccountDao(_login, _password);
        dao account;
        switch (accountType.getValue())
        {
            case CLIENT -> account = new ClientDao(_dao, name.getText(), phone.getText());
            case WORKER -> account = new WorkerDao(_dao, name.getText());
            case ADMIN -> account = new AdminDao(_dao);
            default -> account = null;
        }
        if (!MainApplication.getControllerInstance().save(_dao)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error! This login is already in use", ButtonType.CLOSE);
            alert.setHeaderText("Error! This login is already in use");
            alert.setTitle("Registration error");
            alert.show();
            return;
        }
        if (account != null) {
            MainApplication.getControllerInstance().save(account);
        }
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
