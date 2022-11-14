package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.ReportActivity;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.DirectoryUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

// Класс для отображения списка отчетов
public class ReportListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_list_activity);

        TextView tvOblako =  findViewById(R.id.tvOblako);
        // Кнопка Создать новый отчет
        FloatingActionButton buttonAddNewReport =  findViewById(R.id.addNewFolder);
        buttonAddNewReport.setColorFilter(Color.argb(255, 255, 255, 255));

        // Создаем и назначаем обработчик кнопки создания отчетов
        buttonAddNewReport.setOnClickListener(new NewReportAdder());
        tvOblako.setOnClickListener(view -> startActivity(new Intent(view.getContext(), CloudActivity.class)));

        setAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
    }

    private void setAdapter(){

        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(getApplicationContext()).getReportDao();
        ArrayList <ReportInDB> reportList = (ArrayList<ReportInDB>) reportDAO.getAllReports();


        if (recyclerView == null)  recyclerView = findViewById(R.id.report_rv);

        // Создаем адаптер и назначаем его  recyclerView
        ReportListRVAdapter adapter = new ReportListRVAdapter(reportList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}