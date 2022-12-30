package com.example.testedittext.activities.report_list;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.activities.report_list.server.Server;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.Storage;

public class CloudRvItemHandler  implements View.OnClickListener{

    CloudActivity cloudActivity;
    ReportEntity report;

    public CloudRvItemHandler(CloudActivity cloudActivity, ReportEntity report) {
        this.cloudActivity = cloudActivity;
        this.report = report;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Скачать или удалить");
        builder.setMessage("Скачать отчет на устройство или удалить из базы данных?");
        builder.setPositiveButton("Скачать", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Создание  объекта DAO для работы с БД
                ReportDAO reportDAO = Bd.getAppDatabaseClass(view.getContext().getApplicationContext()).getReportDao();
                // Удаляем отчет из БД
                reportDAO.insertReport(new ReportInDB(report));


            }
        });
        builder.setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Server().deleteReport("userid", report);
                cloudActivity.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
