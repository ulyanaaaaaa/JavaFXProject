package com.example.kursovoi2.client;

import com.example.kursovoi2.MainApplication;
import com.example.kursovoi2.client.hibernate.dao.dao;
import com.example.kursovoi2.client.hibernate.dao.functional.AccountDao;
import com.example.kursovoi2.client.hibernate.dao.functional.ClientDao;
import com.example.kursovoi2.client.hibernate.dao.functional.WorkerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LoginController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    private AccountDao validateAccount(String _login, String _password)
    {
        List<AccountDao> list = MainApplication.getControllerInstance().loadAll(AccountDao.class);

        _password = AccountDao.getPasswordHash(_password, "1234");

        for (AccountDao dao : list) {
            if (Objects.equals(_login, dao.getLogin()) && Objects.equals(_password, dao.getPassword()))
            {
                return dao;
            }
        }
        return null;
    }


    @FXML
    private void LoginAction(ActionEvent event) {
        // This action is called when Sign In button is pressed
        String _login = login.getText();
        String _password = password.getText();

        AccountDao _dao = validateAccount(_login, _password);

        if (_dao == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning!");
            alert.setHeaderText("Incorrect login or/and password!");
            alert.show();
            return;
        }

        if (_dao.isBlocked())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning!");
            alert.setHeaderText("Your account is permanently blocked!");
            alert.show();
            return;
        }

        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("client/mainpage.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.setTitle("Main page");

        dao linkedProfile = MainApplication.getControllerInstance().loadByCriteriaSingle(ClientDao.class, "account", _dao);
        if (linkedProfile != null)
        {
            MainController.setClient((ClientDao) linkedProfile);
        }
        else {
            linkedProfile = MainApplication.getControllerInstance().loadByCriteriaSingle(WorkerDao.class, "account", _dao);
            if (linkedProfile != null) {
                MainController.setWorker((WorkerDao) linkedProfile);
            }
            else {
                fxmlLoader = new FXMLLoader(MainApplication.class.getResource("admin/mainpage.fxml"));
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setScene(scene);
                stage.show();
                fxmlLoader.<AdminController>getController().OnInitialise();
                return;
            }
        }

        fxmlLoader.<MainController>getController().OnInitialise();
        stage.show();
    }

    @FXML
    private void RegisterAction() {
        // This action is called when Sign Up button is pressed

        // Switch to another view
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("register-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.setTitle("Sign up");
        stage.show();

        fxmlLoader.<RegisterView>getController().OnInitialise();
    }

}