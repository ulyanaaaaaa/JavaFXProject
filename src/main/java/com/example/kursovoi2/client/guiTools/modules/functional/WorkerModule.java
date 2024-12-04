package com.example.kursovoi2.client.guiTools.modules.functional;

import com.example.kursovoi2.client.guiTools.modules.module;
import com.example.kursovoi2.client.hibernate.dao.functional.WorkerDao;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;

public class WorkerModule implements module<WorkerDao> {
    @Getter
    private final SimpleStringProperty name;

    public WorkerModule() {
        name = new SimpleStringProperty();
    }

    public WorkerModule(String name) {
        this.name = new SimpleStringProperty(name);
    }

    @Override
    public void convertFrom(WorkerDao dao) {
        this.name.set(dao.getName());
    }

    @Override
    public WorkerDao convertTo() {
        return null;
    }
}
