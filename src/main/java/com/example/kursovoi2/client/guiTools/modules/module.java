package com.example.kursovoi2.client.guiTools.modules;

/*
*   Modules are made to be displayed as TableRow content
*/

import com.example.kursovoi2.client.hibernate.dao.dao;

public interface module<T extends dao> {
    public void convertFrom(T dao);
    @Deprecated
    public T convertTo();
}

