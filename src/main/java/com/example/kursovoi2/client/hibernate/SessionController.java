package com.example.kursovoi2.client.hibernate;

import lombok.Getter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionController {

    /*
            SessionController handles static reference to SessionFactory [org.hibernate] and opens
            private individual Session

            SessionController uses SmartPointer principle storing counter value
    */

    // ######################################### FIELDS ######################################### //
    @Getter
    protected static SessionFactory sessionFactory;
    @Getter
    protected final Session session;
    private static int counter;

    // ###################################### CONSTRUCTORS ###################################### //
    public SessionController()
    {
        counter++;
        if (sessionFactory == null)
        {
            try {
                sessionFactory = new Configuration().configure().buildSessionFactory();

            } catch(HibernateException exception){
                System.out.println("Problem creating session factory");
                exception.printStackTrace();
            }
        }
        session = sessionFactory.openSession();
    }

    public void close() {
        session.close();
        counter--;
        if (counter <= 0)
            sessionFactory.close();
    }
}

