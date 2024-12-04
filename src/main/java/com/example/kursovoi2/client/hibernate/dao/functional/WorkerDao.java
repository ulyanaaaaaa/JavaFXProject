package com.example.kursovoi2.client.hibernate.dao.functional;

import com.example.kursovoi2.client.hibernate.dao.dao;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "worker")
public class WorkerDao implements dao, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @OneToOne
    @JoinColumn(name = "w_account_id", referencedColumnName = "id")
    @Getter
    @Setter
    private AccountDao account;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    public WorkerDao() {
        id = 0;
        account = null;
        name = "";
    }

    public WorkerDao(AccountDao accountDao, String name) {
        this.account = accountDao;
        this.name = name;
    }

    //public String toString() { return name; }
}
