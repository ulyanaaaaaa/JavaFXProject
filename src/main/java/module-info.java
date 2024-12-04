module com.example.kursovoi2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;
    requires org.burningwave.core;
    requires com.jfoenix;

    // Открываем пакеты для работы с JavaFX
    opens com.example.kursovoi2 to javafx.fxml;
    opens com.example.kursovoi2.client.hibernate.dao.functional to org.hibernate.orm.core;
    opens com.example.kursovoi2.API to javafx.fxml; // Открываем пакет API для JavaFX

    exports com.example.kursovoi2;
    exports com.example.kursovoi2.client;
    exports com.example.kursovoi2.API; // Экспортируем пакет API
    opens com.example.kursovoi2.client to javafx.fxml;
}