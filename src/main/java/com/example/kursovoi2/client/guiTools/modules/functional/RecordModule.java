package com.example.kursovoi2.client.guiTools.modules.functional;

import com.example.kursovoi2.client.guiTools.modules.module;
import com.example.kursovoi2.client.hibernate.dao.functional.ClientDao;
import com.example.kursovoi2.client.hibernate.dao.functional.RecordDao;
import com.example.kursovoi2.client.hibernate.dao.functional.WorkerDao;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

import java.sql.Date;
import java.sql.Time;

public class RecordModule implements module<RecordDao> {
    @Getter
    private final ClientModule client;
    @Getter
    private final WorkerModule worker;

    @Getter
    private final SimpleObjectProperty<Date> date;
    @Getter
    private final SimpleObjectProperty<Time> time;

    @Getter
    private RecordDao dao = null;

    public RecordModule() {
        client = new ClientModule();
        worker = new WorkerModule();
        date = new SimpleObjectProperty<>();
        time = new SimpleObjectProperty<>();
    }

    public RecordModule(ClientDao client, WorkerDao worker, Date date, Time time) {
        this.client = new ClientModule(client.getName(), client.getPhone());
        this.worker = new WorkerModule(worker.getName());
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
    }

    @Override
    public void convertFrom(RecordDao dao) {
        client.convertFrom(dao.getClient());
        worker.convertFrom(dao.getWorker());
        date.set(dao.getDate());
        time.set(dao.getTime());
        this.dao = dao;
    }

    @Override
    public RecordDao convertTo() {
        return null;
    }
}
