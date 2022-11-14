package com.example.testedittext.activities.report_list.report.shield_list;

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
import com.example.testedittext.utils.DirectoryUtil;
import com.example.testedittext.utils.Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RenameReportHandler implements View.OnClickListener {

    ReportActivity reportActivity;

    public RenameReportHandler(ReportActivity reportActivity) {
        this.reportActivity = reportActivity;
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();

        // Ввод названия папки
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Переименовать отчет");
        alert.setMessage("Введите название");
        final EditText input = new EditText(context);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = String.valueOf(input.getText());


                // Меняем название в БД
                // Создание  объекта DAO для работы с БД
                ReportDAO reportDAO = Bd.getAppDatabaseClass(context).getReportDao();
                // Получаем из БД отчет
                ReportInDB oldReport = reportDAO.getReportByName(Storage.currentReportEntityStorage.getName());
                Storage.currentReportEntityStorage = oldReport.getReportEntity();

                ReportInDB newReportEntityInDB = reportDAO.getReportByName(Storage.currentReportEntityStorage.getName());
                ReportEntity newReportEntity = newReportEntityInDB.getReportEntity();
                newReportEntity.setName(value);
                reportDAO.deleteReport(new ReportInDB(oldReport.getReportEntity()));
                reportDAO.insertReport(new ReportInDB(newReportEntity));

                Storage.currentReportEntityStorage = newReportEntity;


                Toast toast = Toast.makeText(context, "Отчет переименован!",Toast.LENGTH_SHORT);
                toast.show();


                // Устанавливаем текущий отчет
                Storage.currentReportEntityStorage = newReportEntity;
                // Меняем заголовок
                reportActivity.changeTitle();

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
