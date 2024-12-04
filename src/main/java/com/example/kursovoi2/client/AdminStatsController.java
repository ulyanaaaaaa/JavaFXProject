package com.example.kursovoi2.client;

import com.example.kursovoi2.MainApplication;
import com.example.kursovoi2.client.hibernate.dao.ClientDaoWrapper;
import com.example.kursovoi2.client.hibernate.dao.WorkerDaoWrapper;
import com.example.kursovoi2.client.hibernate.dao.functional.ClientDao;
import com.example.kursovoi2.client.hibernate.dao.functional.RecordDao;
import com.example.kursovoi2.client.hibernate.dao.functional.WorkerDao;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class AdminStatsController {
    @FXML
    private PieChart chart1;

    @FXML
    private StackedBarChart<String, Integer> chart2;

    public void OnInitialise()
    {
        // Pie chart setup
        List<WorkerDao> daoList = MainApplication.getControllerInstance().loadAll(WorkerDao.class);

        for (WorkerDao dao : daoList)
        {
            List<RecordDao> recordsList = MainApplication.getControllerInstance().loadByCriteria(RecordDao.class, "worker", dao);
            chart1.getData().add(new PieChart.Data(dao.getName(), recordsList.size()));
        }

        // Stacked bar chart setup
        List<ClientDao> clientsDaoList = MainApplication.getControllerInstance().loadAll(ClientDao.class);
        for (ClientDao dao : clientsDaoList)
        {
            List<RecordDao> recordsList = MainApplication.getControllerInstance().loadByCriteria(RecordDao.class, "client", dao);
            XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
            series.getData().add(new XYChart.Data<>(dao.getName(), recordsList.size()));
            chart2.getData().add(series);
        }
    }
}
