package com.example.testedittext.activities.report_list.report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.basic_information.BasicInformationActivity;
import com.example.testedittext.activities.report_list.report.ground.GroundActivity;
import com.example.testedittext.activities.report_list.report.shield_list.RenameReportHandler;
import com.example.testedittext.activities.report_list.report.shield_list.ShieldListActivity;
import com.example.testedittext.utils.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


// Класс для редактирования отчета
public class ReportActivity extends AppCompatActivity {

    TextView reportTitle;
    ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);


        progressBar =  findViewById(R.id.progressBar2);

        // Кнопка сохранить отчет на сервер
        FloatingActionButton reportSave =  findViewById(R.id.reportSave);
        reportSave.setColorFilter(Color.argb(255, 255, 255, 255));
        // Кнопка поделиться
        FloatingActionButton buttonShare =  findViewById(R.id.shareReport);
        buttonShare.setColorFilter(Color.argb(255, 255, 255, 255));
        // Кнопка переименовать отчет
        FloatingActionButton buttonRename =  findViewById(R.id.renameReport);
        buttonRename.setColorFilter(Color.argb(255, 255, 255, 255));
        // Кнопка удалить отчет
        FloatingActionButton buttonDelete =  findViewById(R.id.deleteReport);
        buttonDelete.setColorFilter(Color.argb(255, 255, 255, 255));
        // TV название отчета
        reportTitle = findViewById(R.id.reportTitle);
        // TV Основная информация
        TextView addInf = findViewById(R.id.addInf);
        // TV Щиты и помещения
        TextView shieldsList = findViewById(R.id.addShield);
        // TV Заземление и молниезащита
        TextView ground = findViewById(R.id.tvGround);
        // TV Посмотреть отчет
        TextView viewReport = findViewById(R.id.tvViewReport);


        // Устанавливаем в TV название отчета
        changeTitle();



        // Назначаем обработчик кнопке сохранить отчет
        //buttonShare.setOnClickListener(new ShareReportHandler());
        // Назначаем обработчик кнопке поделиться
        buttonShare.setOnClickListener(new ShareReportHandler(progressBar));
        // Назначаем обработчик кнопке переименовать
        buttonRename.setOnClickListener(new RenameReportHandler(this));
        // Назначаем обработчик кнопке удалить отчет
        buttonDelete.setOnClickListener(new DeleteReportHandler(this));
        // Назначаем обработчик тексту Основн. инф.
        addInf.setOnClickListener(view -> startActivity(new Intent(view.getContext(), BasicInformationActivity.class)));
        // Назначаем обработчик тексту Щиты и помещения
        shieldsList.setOnClickListener((view -> startActivity(new Intent(view.getContext(), ShieldListActivity.class))));
        // Назначаем обработчик тексту Щиты и помещения
        ground.setOnClickListener((view -> startActivity(new Intent(view.getContext(), GroundActivity.class))));
        // Назначаем обработчик TV Посмотреть отчет
        viewReport.setOnClickListener(new ViewReportHandler(progressBar));
        // Назначаем обработчик кнопке сохранить отчет на сервер
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        reportSave.setOnClickListener(new SaveReportOnServerHandler());



/////////////////////////////////////////////////////////////////////////////////////


    }

    public String getreportname(){ return Storage.currentReportEntityStorage.getName().toString();}


    @Override
    protected void onPause() {
        super.onPause();
        progressBar.setVisibility(View.GONE);
    }

    public void changeTitle() {
        // Устанавливаем в TV название отчета
        reportTitle.setText(Storage.currentReportEntityStorage.getName());
    }

}