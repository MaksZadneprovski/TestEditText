package com.example.testedittext.activities.report_list.report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.basic_information.BasicInformationActivity;
import com.example.testedittext.activities.report_list.report.ground.GroundActivity;
import com.example.testedittext.activities.report_list.report.shield_list.RenameReportHandler;
import com.example.testedittext.activities.report_list.report.shield_list.ShieldListActivity;
import com.example.testedittext.click_handlers.test.MeasureInsulationAdder;
import com.example.testedittext.click_handlers.test.MeasureInsulationSaver;
import com.example.testedittext.report_creator.Report;
import com.example.testedittext.utils.Storage;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.DirectoryUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

// Класс для редактирования отчета
public class ReportActivity extends AppCompatActivity {

    ReportEntity reportEntity;
    TextView reportTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        // Получаем reportEntity из БД или создаем если не было
        getActualReportEntity();


        // Кнопка сохранить отчет
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




        // Устанавливаем в TV название отчета
        reportTitle.setText(DirectoryUtil.getCurrentFolder());

        // Назначаем обработчик кнопке сохранить отчет
        buttonShare.setOnClickListener(new ShareReportHandler());
        // Назначаем обработчик кнопке поделиться
        buttonShare.setOnClickListener(new ShareReportHandler());
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

/////////////////////////////////////////////////////////////////////////////////////




    }

    @Override
    protected void onResume() {
        super.onResume();
        getActualReportEntity();
    }


    public void getActualReportEntity(){
        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(getApplicationContext()).getReportDao();

        // Получаем из БД отчет
        ReportInDB report = reportDAO.getReportByPath(DirectoryUtil.currentDirectory);
        // Создаем новый и сохраняем его в БД, если его не было
        if (report == null){
            Storage.currentReportEntityStorage = new ReportEntity(DirectoryUtil.currentDirectory);
            reportDAO.insertReport(new ReportInDB(Storage.currentReportEntityStorage));
        }else {
            // Если был, получаем объект отчет
            Storage.currentReportEntityStorage = report.getReportEntity();
        }

    }


    public void changeTitle() {
        // Устанавливаем в TV название отчета
        reportTitle.setText(DirectoryUtil.getCurrentFolder());
    }
}