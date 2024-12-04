package com.example.kursovoi2.client.hibernate.dao;

import com.example.kursovoi2.client.hibernate.dao.functional.ClientDao;
import lombok.Getter;

@Getter
public class ClientDaoWrapper {
    private final ClientDao referenced;

    public ClientDaoWrapper(ClientDao dao)
    {
        referenced = dao;
    }

    @Override
    public String toString() {
        return referenced.getName() + " " + referenced.getPhone();
    }
}
