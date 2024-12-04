package com.example.kursovoi2.client.guiTools.modules.functional;

import com.example.kursovoi2.client.guiTools.modules.module;
import com.example.kursovoi2.client.hibernate.dao.functional.ClientDao;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;

public class ClientModule implements module<ClientDao> {
    @Getter
    private final SimpleStringProperty name;
    @Getter
    private final SimpleStringProperty phone;

    public ClientModule() {
        name = new SimpleStringProperty();
        phone = new SimpleStringProperty();
    }

    public ClientModule(String name, String phone)
    {
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
    }

    @Override
    public void convertFrom(ClientDao dao) {
        name.set(dao.getName());
        phone.set(dao.getPhone());
    }

    @Override
    public ClientDao convertTo() {
        return null;
    }
}
