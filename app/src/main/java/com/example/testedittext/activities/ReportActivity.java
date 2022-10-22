package com.example.testedittext.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.click_handlers.AddShieldHandler;
import com.example.testedittext.click_handlers.DeleteReportHandler;
import com.example.testedittext.click_handlers.ShareReportHandler;
import com.example.testedittext.click_handlers.AddBasicInformationHandler;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.DirectoryUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// Класс для редактирования отчета
public class ReportActivity extends AppCompatActivity {

    ReportEntity reportEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        // Получаем reportEntity из БД или создаем если не было
        getActualReportEntity();

        /////////////////////////////////////////////////////////////////////////////////////////
        // Выводим все, что есть в БД
        // reportDAO.getAllReports().stream().forEach(x -> System.out.println(x));
        /////////////////////////////////////////////////////////////////////////////////////////



        // Кнопка сохранить отчет
        FloatingActionButton reportSave =  findViewById(R.id.reportSave);
        // Кнопка поделиться
        FloatingActionButton buttonShare =  findViewById(R.id.shareReport);
        // Кнопка удалить отчет
        FloatingActionButton buttonDelete =  findViewById(R.id.deleteReport);
        // TV название отчета
        TextView reportTitle = findViewById(R.id.reportTitle);
        // TV Основная информация
        TextView addInf = findViewById(R.id.addInf);
        // TV Щиты и помещения
        TextView addShield = findViewById(R.id.addShield);




        // Устанавливаем в TV название отчета
        reportTitle.setText(DirectoryUtil.getCurrentFolder());

        // Назначаем обработчик кнопке сохранить отчет
        buttonShare.setOnClickListener(new ShareReportHandler());
        // Назначаем обработчик кнопке поделиться
        buttonShare.setOnClickListener(new ShareReportHandler());
        // Назначаем обработчик кнопке удалить отчет
        buttonDelete.setOnClickListener(new DeleteReportHandler(this));
        // Назначаем обработчик тексту Основн. инф.
        addInf.setOnClickListener(new AddBasicInformationHandler(reportEntity));
        // Назначаем обработчик тексту Щиты и помещения
        addShield.setOnClickListener(new AddShieldHandler(reportEntity));




    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStop() {
        super.onStop();
        DirectoryUtil.currentDirectory = null;
    }

    public void getActualReportEntity(){
        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(getApplicationContext()).getReportDao();

        // Получаем из БД отчет
        ReportInDB report = reportDAO.getReportByPath(DirectoryUtil.currentDirectory);
        // Создаем новый и сохраняем его в БД, если его не было
        if (report == null){
            reportEntity = new ReportEntity(DirectoryUtil.currentDirectory);
            reportDAO.insertReport(new ReportInDB(reportEntity));
        }else {
            // Если был, получаем объект отчет
            reportEntity = report.getReportEntity();
        }
    }


}