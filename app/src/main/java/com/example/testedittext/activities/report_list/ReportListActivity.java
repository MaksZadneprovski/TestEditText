package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.admin.account.AccountActivity;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.DefectsParser;
import com.example.testedittext.utils.SortReport;
import com.example.testedittext.utils.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;


// Класс для отображения списка отчетов
public class ReportListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ReportInDB> reportList;
    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    Context context;
    FloatingActionButton buttonAddNewReport;
    FloatingActionButton admin;
    FloatingActionButton sort;
    FloatingActionButton settings;
    FloatingActionButton statistics;
    FloatingActionButton cloud;
    private boolean areButtonsVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.report_list_activity);

        // Создаем объект, так как там есть метод, вызываемый в конструкторе, который парсит дефекты из файла
        new Storage(this).parseDefects();

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String login = sharedPreferences.getString("login", "");

        // Кнопка Создать новый отчет
        buttonAddNewReport = findViewById(R.id.addNewFolder);
        admin = findViewById(R.id.admin);
        sort = findViewById(R.id.sort);
        settings = findViewById(R.id.settings);
        statistics = findViewById(R.id.statistics);
        cloud = findViewById(R.id.cloud);
        admin.setColorFilter(Color.argb(255, 255, 255, 255));
        buttonAddNewReport.setColorFilter(Color.argb(255, 255, 255, 255));
        sort.setColorFilter(Color.argb(255, 255, 255, 255));
        settings.setColorFilter(Color.argb(255, 255, 255, 255));
        statistics.setColorFilter(Color.argb(255, 255, 255, 255));
        cloud.setColorFilter(Color.argb(255, 255, 255, 255));


        String adminName = getResources().getString(R.string.admin);

        if (login.equals(adminName)) {
            admin.setVisibility(View.VISIBLE);
            statistics.setVisibility(View.VISIBLE);
        }
        else {
            admin.setVisibility(View.INVISIBLE);
            statistics.setVisibility(View.INVISIBLE);
        }



        setAdapter();

        // Создаем и назначаем обработчик кнопки создания отчетов
        admin.setOnClickListener(view -> startActivity(new Intent(view.getContext(), AccountActivity.class)));
        buttonAddNewReport.setOnClickListener(new NewReportAdder());
        sort.setOnClickListener(new SortReport(this, recyclerView));


        cloud.setOnClickListener(view -> {
            Intent intent = new Intent(this, CloudActivity.class);
            intent.putExtra("login", login);
            startActivity(intent);
        });

        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsActivity.class)));
        statistics.setOnClickListener(view -> startActivity(new Intent(view.getContext(), StatisticsActivity.class)));

    }


    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String login = sharedPreferences.getString("login", "");
        String adminName = getResources().getString(R.string.admin);
        FloatingActionButton admin =  findViewById(R.id.admin);
        if (login.equals(adminName)) admin.setVisibility(View.VISIBLE);
        else admin.setVisibility(View.INVISIBLE);
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