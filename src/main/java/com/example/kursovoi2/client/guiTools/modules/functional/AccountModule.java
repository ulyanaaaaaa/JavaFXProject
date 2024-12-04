package com.example.kursovoi2.client.guiTools.modules.functional;

import com.example.kursovoi2.client.guiTools.modules.module;
import com.example.kursovoi2.client.hibernate.dao.functional.AccountDao;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

public class AccountModule implements module<AccountDao> {
    @Getter
    @Setter
    private SimpleStringProperty login;

    @Getter
    @Setter
    private SimpleBooleanProperty blocked; // Change to SimpleBooleanProperty

    @Getter
    @Setter
    private AccountDao referenced;

    public AccountModule() {
        login = new SimpleStringProperty();
        blocked = new SimpleBooleanProperty(); // Initialize as boolean
    }

    public AccountModule(String login, boolean isBlocked) {
        this.login = new SimpleStringProperty(login);
        this.blocked = new SimpleBooleanProperty(isBlocked); // Set boolean directly
    }

    @Override
    public void convertFrom(AccountDao dao) {
        referenced = dao;
        login.set(dao.getLogin());
        blocked.set(dao.isBlocked()); // Directly set the boolean value
    }

    @Override
    public AccountDao convertTo() {
        // Implement conversion logic if needed
        return null;
    }

    // Method to get the blocked status as a boolean
    public boolean isBlocked() {
        return blocked.get(); // Return the boolean value
    }
}