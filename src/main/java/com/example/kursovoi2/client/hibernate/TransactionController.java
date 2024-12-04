package com.example.kursovoi2.client.hibernate;

import com.example.kursovoi2.client.hibernate.dao.dao;
import lombok.Getter;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;

public class TransactionController extends SessionController {
    @Getter
    private Transaction transaction;

    public void begin() {
        if (transaction != null && transaction.isActive())
        {
            session.clear();
        }
        transaction = session.beginTransaction();
    }

    public void save(dao ob) {
        session.saveOrUpdate(ob);
    }
    public void delete(dao ob)
    {
        session.delete(ob);
    }
    public <T extends dao> List<T> loadAll(Class<T> _class)
    {
        return session.createQuery("from " + _class.getName()).list();
    }

    public <T extends dao, V extends Serializable> List<T> loadByCriteria(Class<T> _class, String _key, V _value) {
        Query<T> query = session.createQuery("from " + _class.getName() + " c where c." + _key + "=:" + _key, _class);
        query.setParameter(_key, _value);
        try {
            return query.list();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    public <T extends dao, V extends Serializable> T loadByCriteriaSingle(Class<T> _class, String _key, V _value) {
        Query<T> query = session.createQuery("from " + _class.getName() + " c where c." + _key + "=:" + _key, _class);
        query.setParameter(_key, _value);
        try {
            return query.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
    }

    public void commit() {
        transaction.commit();
    }
    public void rollback() { transaction.rollback(); }
}
