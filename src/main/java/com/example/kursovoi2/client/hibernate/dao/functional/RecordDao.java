package com.example.kursovoi2.client.hibernate.dao.functional;

import com.example.kursovoi2.client.hibernate.dao.dao;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "client_record")
public class RecordDao implements dao, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @Getter
    @Setter
    private ClientDao client;

    @ManyToOne
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    @Getter
    @Setter
    private WorkerDao worker;

    @Column(name = "date")
    @Getter
    @Setter
    private Date date;

    @Column(name = "time")
    @Getter
    @Setter
    private Time time;

    public RecordDao()
    {
        id = 0;
        client = null;
        worker = null;
        date = null;
        time = null;
    }

    public RecordDao(ClientDao clientDao, WorkerDao workerDao, Date date, Time time)
    {
        this.client = clientDao;
        this.worker = workerDao;
        this.date = date;
        this.time = time;
    }
}
