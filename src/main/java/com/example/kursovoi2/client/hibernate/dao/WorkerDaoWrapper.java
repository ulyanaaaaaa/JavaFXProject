package com.example.kursovoi2.client.hibernate.dao;

import com.example.kursovoi2.client.hibernate.dao.functional.WorkerDao;
import lombok.Getter;

@Getter
public class WorkerDaoWrapper {
    private WorkerDao referenced;

    public WorkerDaoWrapper(WorkerDao dao) {
        this.referenced = dao;
    }
    @Override
    public String toString() { return referenced.getName(); }
}
