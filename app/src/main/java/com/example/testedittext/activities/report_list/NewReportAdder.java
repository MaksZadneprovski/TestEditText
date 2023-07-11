package com.example.testedittext.activities.report_list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.activities.report_list.report.ReportActivity;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

// Добавляет новую папку-отчет
public class NewReportAdder implements View.OnClickListener{

    @Override
    public void onClick(View view) {

        Context context = view.getContext();
        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(view.getContext()).getReportDao();
        ArrayList <ReportInDB> reportList = (ArrayList<ReportInDB>) reportDAO.getAllReports();
        ArrayList <String> nameReportList = new ArrayList<>();
        for (ReportInDB report : reportList) {
            nameReportList.add(report.getName());
        }

        // Ввод названия
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Создать отчет");
        alert.setMessage("Введите название");
        final EditText input = new EditText(context);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = String.valueOf(input.getText());
                //Path path = Paths.get(view.getContext().getExternalFilesDir(null) + "/" + value);

                //if (!Files.exists(path)) {
                if (!nameReportList.contains(value)) {
//
                    ReportEntity reportEntity = new ReportEntity(value);
                    reportDAO.insertReport(new ReportInDB(reportEntity));
                    Storage.currentReportEntityStorage = reportEntity;
                    Intent intent = new Intent(context, ReportActivity.class);
                    context.startActivity(intent);
                }else {
                    Toast toast = Toast.makeText(view.getContext(), "Отчет с таким названием уже существует!",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
        ////////////////



    }
}
