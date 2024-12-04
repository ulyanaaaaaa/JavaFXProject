package com.example.kursovoi2.API;

import com.example.kursovoi2.client.hibernate.dao.dao;
import lombok.Setter;
import org.hibernate.HibernateException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

public class ClientConnection implements Runnable {
    private Socket connection;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientConnection(Socket socket) { connection = socket; }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }


    @Override
    public void run() {
        // Client thread
        System.out.println("Client connected to thread");
        try {
            ois = new ObjectInputStream(connection.getInputStream());
            oos = new ObjectOutputStream(connection.getOutputStream());
            String command;
            while ((command = (String) ois.readObject()) != null) {
                switch (command)
                {
                    case "Handshake" -> {
                        System.out.println("Handshaked!");
                    }
                    case "Save" -> {
                        System.out.println("Save action");
                        dao _dao = (dao) ois.readObject();
                        try {
                            Server.getController().begin();
                            Server.getController().save(_dao);
                            Server.getController().commit();
                            oos.writeObject(true);
                        }
                        catch (HibernateException e)
                        {
                            oos.writeObject(false);
                        }
                    }
                    case "Delete" -> {
                        System.out.println("Delete action");
                        dao _dao = (dao) ois.readObject();
                        Server.getController().getSession().clear();
                        Server.getController().begin();
                        Server.getController().delete(_dao);
                        Server.getController().commit();
                    }
                    case "LoadAll" -> {
                        System.out.println("LoadAll");
                        Class _class = (Class) ois.readObject();
                        List daoList = Server.getController().loadAll(_class);
                        oos.writeObject(daoList);
                    }
                    case "LoadByCriteria" -> {
                        System.out.println("LoadCriteria");
                        Class _class = (Class) ois.readObject();
                        String param = (String) ois.readObject();
                        Serializable value = (Serializable) ois.readObject();

                        List daoList = Server.getController().loadByCriteria(_class, param, value);
                        oos.writeObject(daoList);
                    }
                    case "LoadByCriteriaSingle" -> {
                        System.out.println("LoadCriteriaSingle");
                        Class _class = (Class) ois.readObject();
                        String param = (String) ois.readObject();
                        Serializable value = (Serializable) ois.readObject();

                        oos.writeObject(Server.getController().loadByCriteriaSingle(_class, param, value));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
