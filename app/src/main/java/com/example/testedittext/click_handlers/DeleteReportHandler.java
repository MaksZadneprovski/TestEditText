package com.example.testedittext.click_handlers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.Storage;
import com.example.testedittext.activities.ReportActivity;
import com.example.testedittext.activities.ReportListActivity;
import com.example.testedittext.adapters.Reports_LV_Adapter;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.DirectoryUtil;

import java.io.File;

public class DeleteReportHandler implements  AdapterView.OnClickListener{

    ReportActivity reportActivity;

    public DeleteReportHandler(ReportActivity reportActivity) {
        this.reportActivity = reportActivity;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Удаление");
        builder.setMessage("Вы действительно хотите удалить папку?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Создание  объекта DAO для работы с БД
                ReportDAO reportDAO = Bd.getAppDatabaseClass(view.getContext().getApplicationContext()).getReportDao();
                // Удаляем отчет из БД
                reportDAO.deleteReport(new ReportInDB(Storage.reportEntityStorage));

                DirectoryUtil.deleteDirectory(new File(DirectoryUtil.currentDirectory));
                reportActivity.finish();
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
