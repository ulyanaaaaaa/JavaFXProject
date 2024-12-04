package com.example.kursovoi2.client.hibernate.dao.functional;

import com.example.kursovoi2.client.hibernate.dao.dao;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "admins")
public class AdminDao implements dao, Serializable {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "a_account_id", referencedColumnName = "id")
    private AccountDao account;

    public AdminDao()
    {
        account = null;
    }

    public AdminDao(AccountDao _dao)
    {
        this.account = _dao;
    }
}
