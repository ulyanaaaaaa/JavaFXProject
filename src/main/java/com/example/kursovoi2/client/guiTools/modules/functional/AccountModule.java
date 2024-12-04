package com.example.kursovoi2.client.guiTools.modules.functional;

import com.example.kursovoi2.client.guiTools.modules.module;
import com.example.kursovoi2.client.hibernate.dao.functional.AccountDao;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

public class AccountModule implements module<AccountDao> {
    @Getter
    @Setter
    private SimpleStringProperty login;

    @Getter
    @Setter
    private SimpleStringProperty blocked;

    @Getter
    @Setter
    private AccountDao referenced;

    public AccountModule()
    {
        blocked = new SimpleStringProperty();
        login = new SimpleStringProperty();
    }

    public AccountModule(String login, boolean isBlocked) {
        this.login = new SimpleStringProperty(login);
        this.blocked = new SimpleStringProperty((isBlocked) ? "blocked" : "active");
    }

    @Override
    public void convertFrom(AccountDao dao) {
        referenced = dao;
        login.set(dao.getLogin());
        blocked.set((dao.isBlocked()) ? "blocked" : "active");
    }

    @Override
    public AccountDao convertTo() {
        return null;
    }
}
