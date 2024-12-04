package com.example.kursovoi2.client;

import com.example.kursovoi2.MainApplication;
import com.example.kursovoi2.client.guiTools.modules.functional.AccountModule;
import com.example.kursovoi2.client.hibernate.dao.functional.AccountDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.List;

public class FindAccountDialogController {

    @FXML
    private TextField loginField;

    private AdminController adminController; // Ссылка на AdminController

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    @FXML
    private void handleSearch() {
        String login = loginField.getText();
        if (login != null && !login.isEmpty()) {
            AccountDao account = searchAccountByLogin(login);
            if (account != null) {
                adminController.tableView.getItems().clear(); // Очищаем текущие записи
                AccountModule module = new AccountModule();
                module.convertFrom(account);
                adminController.tableView.getItems().add(module); // Добавляем найденный аккаунт
            } else {
                // Обработка случая, когда аккаунт не найден
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Account not found!", ButtonType.OK);
                alert.setTitle("Information");
                alert.showAndWait();
            }
        }
    }

    private AccountDao searchAccountByLogin(String login) {
        // Получаем список всех аккаунтов из базы данных
        List<AccountDao> accounts = MainApplication.getControllerInstance().loadAll(AccountDao.class);

        // Ищем аккаунт с заданным логином
        for (AccountDao account : accounts) {
            if (account.getLogin().equalsIgnoreCase(login)) { // Сравниваем логины без учета регистра
                return account; // Возвращаем найденный аккаунт
            }
        }
        return null; // Если аккаунт не найден, возвращаем null
    }
}