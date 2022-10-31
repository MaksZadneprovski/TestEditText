package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.testedittext.R;
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

        // Кнопка Создать новый отчет
        FloatingActionButton buttonAddNewReport =  findViewById(R.id.addNewFolder);

        // Создаем и назначаем обработчик кнопки создания отчетов
        buttonAddNewReport.setOnClickListener(new NewReportAdder());

        setAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
    }

    private void setAdapter(){
        if (recyclerView == null)  recyclerView = findViewById(R.id.report_rv);
        // Список отчетов, которые есть в папке хранилище приложения
        ArrayList <File> reportList =  DirectoryUtil.getReportList(this.getExternalFilesDir(null).toString());
        // Создаем адаптер и назначаем его  recyclerView
        ReportListRVAdapter adapter = new ReportListRVAdapter(reportList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}