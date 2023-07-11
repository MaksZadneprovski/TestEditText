package com.example.testedittext.activities.report_list.report;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.Storage;

public class CopyReportHandler implements  AdapterView.OnClickListener{
    @Override
    public void onClick(View view) {

        Context context = view.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Копирование");
        builder.setMessage("Создать копию отчёта?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ReportEntity copyreport = Storage.currentReportEntityStorage;
                // Создание  объекта DAO для работы с БД
                ReportDAO reportDAO = Bd.getAppDatabaseClass(view.getContext().getApplicationContext()).getReportDao();
                ReportEntity copyReport  = Storage.currentReportEntityStorage;
                copyReport.setName(copyReport.getName() + " копия");
                reportDAO.insertReport(new ReportInDB(copyreport));

                Toast.makeText(context, "Копия создана", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();




    }
}
