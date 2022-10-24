package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.testedittext.R;
import com.example.testedittext.utils.DirectoryUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

// Класс для отображения списка отчетов
public class ReportListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_list_activity);

        // Кнопка Создать новый отчет
        FloatingActionButton buttonAddNewReport =  findViewById(R.id.addNewFolder);

        // ListView
        ListView listView = findViewById(R.id.folderListView);

        // Список отчетов, которые есть в папке хранилище приложения
        ArrayList <File> reportList =  DirectoryUtil.getReportList(this.getExternalFilesDir(null).toString());

        // Создаем и назначаем обработчик кнопки создания отчетов
        NewReportAdder newReportAdder = new NewReportAdder(reportList, listView);
        buttonAddNewReport.setOnClickListener(newReportAdder);

        // Создаем адаптер и назначаем его  listView
        Reports_LV_Adapter adapter = new Reports_LV_Adapter(this, reportList);
        listView.setAdapter(adapter);

        // Назначаем listView обработчик нажатия на элемент списка
        listView.setOnItemClickListener(new Reports_LV_ItemClickHandler());

    }

    @Override
    protected void onResume() {
        super.onResume();
        // ListView
        ListView listView = findViewById(R.id.folderListView);

        // Список отчетов, которые есть в папке хранилище приложения
        ArrayList <File> reportList =  DirectoryUtil.getReportList(this.getExternalFilesDir(null).toString());

        // Создаем адаптер и назначаем его  listView
        Reports_LV_Adapter adapter = new Reports_LV_Adapter(this, reportList);
        listView.setAdapter(adapter);
    }
}