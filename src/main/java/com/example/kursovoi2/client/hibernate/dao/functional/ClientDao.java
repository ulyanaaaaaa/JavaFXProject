package com.example.kursovoi2.client.hibernate.dao.functional;

import com.example.kursovoi2.client.hibernate.dao.dao;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "client")
public class ClientDao implements dao, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private int id;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @Getter
    @Setter
    private AccountDao account;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "mobile")
    @Getter
    @Setter
    private String phone;

    public ClientDao() {
        id = 0;
        account = null;
        name = "";
        phone = "";
    }

    public ClientDao(AccountDao accountDao, String name, String phone) {
        this.account = accountDao;
        this.name = name;
        this.phone = phone;
    }

    //public String toString() { return name + " (" + phone + ")"; }
}
