package com.example.kursovoi2.API;

import com.example.kursovoi2.client.hibernate.dao.dao;
import lombok.Getter;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

public class NetworkController {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public NetworkController(String host)
    {
        try {
            System.out.println("Opening host");
            socket = new Socket(host, 1234);
            System.out.println("Opening transports");
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connection established");
            System.out.println("Attempting handshake...");
            oos.writeObject("Handshake");

            System.out.println("Handshake sent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(dao ob) {
        try {
            oos.writeObject("Save");
            oos.writeObject(ob);
            return (boolean) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(dao ob)
    {
        try {
            oos.writeObject("Delete");
            oos.writeObject(ob);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public <T extends dao> List<T> loadAll(Class<T> _class)
    {
        List<T> list = null;
        try {
            oos.writeObject("LoadAll");
            oos.writeObject(_class);
            list = (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public <T extends dao, V extends Serializable> List<T> loadByCriteria(Class<T> _class, String _key, V _value) {
        List<T> list = null;
        try {
            oos.writeObject("LoadByCriteria");
            oos.writeObject(_class);
            oos.writeObject(_key);
            oos.writeObject(_value);
            list = (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public <T extends dao, V extends Serializable> T loadByCriteriaSingle(Class<T> _class, String _key, V _value) {
        T ob = null;
        try {
            oos.writeObject("LoadByCriteriaSingle");
            oos.writeObject(_class);
            oos.writeObject(_key);
            oos.writeObject(_value);
            ob = (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ob;
    }
}
