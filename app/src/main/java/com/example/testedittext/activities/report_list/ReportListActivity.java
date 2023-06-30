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
import com.example.testedittext.utils.ViewEditor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


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
    FloatingActionButton cloud,  showbutton;
    List<View> viewList;
    TextView  tv1, tv2, tv3, tv4, tv5 ,tv6;
    private boolean isBtnHide = true;
    View dimView;

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
        showbutton = findViewById(R.id.showButton);
        dimView = findViewById(R.id.dimView);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);

        admin.setColorFilter(Color.argb(255, 255, 255, 255));
        buttonAddNewReport.setColorFilter(Color.argb(255, 255, 255, 255));
        sort.setColorFilter(Color.argb(255, 255, 255, 255));
        settings.setColorFilter(Color.argb(255, 255, 255, 255));
        statistics.setColorFilter(Color.argb(255, 255, 255, 255));
        cloud.setColorFilter(Color.argb(255, 255, 255, 255));
        showbutton.setColorFilter(Color.argb(255, 255, 255, 255));


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
        showbutton.setOnClickListener(view -> setVisibilityButtons());
        settings.setOnClickListener(view -> startActivity(new Intent(view.getContext(), SettingsActivity.class)));
        statistics.setOnClickListener(view -> startActivity(new Intent(view.getContext(), StatisticsActivity.class)));

        viewList = new ArrayList<>(Arrays.asList(admin,buttonAddNewReport, sort, cloud, settings,statistics, tv1, tv2, tv3, tv4, tv5, tv6));

    }


    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
        setAdminPrivelegions();
    }

    private void setAdminPrivelegions() {
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String login = sharedPreferences.getString("login", "");
        String adminName = getResources().getString(R.string.admin);
        if (login.equals(adminName)) {
            admin.setVisibility(View.VISIBLE);
            statistics.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);

        } else {
            admin.setVisibility(View.GONE);
            statistics.setVisibility(View.GONE);
            tv1.setVisibility(View.GONE);
            tv2.setVisibility(View.GONE);
        }
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

    public void setVisibilityButtons(){
        if (isBtnHide) {
            ViewEditor.showButtons(viewList, dimView) ;
            setAdminPrivelegions();
            isBtnHide = false;
        }
        else{
            ViewEditor.hideButtons(viewList, dimView);
            isBtnHide = true;
        }
    }

}