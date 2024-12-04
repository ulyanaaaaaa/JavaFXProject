package com.example.kursovoi2.client.hibernate.dao.functional;

import com.example.kursovoi2.client.hibernate.dao.dao;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Base64;

@Entity
@Table(name = "account")
public class AccountDao implements dao, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    @Column(name = "login")
    private String login;

    @Getter
    @Setter
    @Column(name = "password")
    private String password;   // WARNING! Password is being stored salted and hashed. Pass UI password through a password crypto module before checking it

    @Getter
    @Setter
    @Column(name = "blocked")
    private boolean blocked;

    public AccountDao() {
        login = "";
        password = "";
    }

    public AccountDao(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static String getPasswordHash(String password, String salt)
    {
        password += salt;
        try {
            byte[] md5 = MessageDigest.getInstance("MD5").digest(password.getBytes());
            return Base64.getEncoder().encodeToString(md5);
        }
        catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }
}
