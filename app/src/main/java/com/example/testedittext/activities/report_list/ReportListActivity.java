package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.admin.AdminActivity;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.SortReport;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;


// Класс для отображения списка отчетов
public class ReportListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList <ReportInDB> reportList;
    TextView tvOblako;
    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.report_list_activity);

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        tvOblako =  findViewById(R.id.tvOblako);
        // Кнопка Создать новый отчет
            FloatingActionButton buttonAddNewReport =  findViewById(R.id.addNewFolder);
        FloatingActionButton admin =  findViewById(R.id.admin);
        FloatingActionButton sort =  findViewById(R.id.sort);
        FloatingActionButton settings =  findViewById(R.id.settings);
        admin.setColorFilter(Color.argb(255, 255, 255, 255));
        buttonAddNewReport.setColorFilter(Color.argb(255, 255, 255, 255));
        sort.setColorFilter(Color.argb(255, 255, 255, 255));
        settings.setColorFilter(Color.argb(255, 255, 255, 255));

        setAdapter();

        // Создаем и назначаем обработчик кнопки создания отчетов
        admin.setOnClickListener(view -> startActivity(new Intent(view.getContext(), AdminActivity.class)));
        buttonAddNewReport.setOnClickListener(new NewReportAdder());
        sort.setOnClickListener(new SortReport(this, recyclerView));

        String login = sharedPreferences.getString("login", "");
        tvOblako.setOnClickListener(view -> {
            Intent intent = new Intent(this, CloudActivity.class);
            intent.putExtra("login", login);
            startActivity(intent);
        });

        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsActivity.class)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
    }


    private void setAdapter(){

        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(getApplicationContext()).getReportDao();
        reportList = (ArrayList<ReportInDB>) reportDAO.getAllReports();

        if (recyclerView == null)  recyclerView = findViewById(R.id.report_rv);
        Collections.reverse(reportList);

        changeAdapter(reportList);
    }

    public void changeAdapter(ArrayList<ReportInDB> reportList){

        if (recyclerView == null)  recyclerView = findViewById(R.id.report_rv);

        // Создаем адаптер и назначаем его  recyclerView
        ReportListRVAdapter adapter = new ReportListRVAdapter(reportList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}