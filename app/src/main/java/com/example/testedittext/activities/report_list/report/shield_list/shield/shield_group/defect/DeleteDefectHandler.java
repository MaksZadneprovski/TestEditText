package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.defect;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.activities.report_list.report.shield_list.shield.ShieldActivity;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.Defect;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

public class DeleteDefectHandler implements  AdapterView.OnClickListener{

    DefectActivity defectActivity;
    int numberOfPressedElement;

    public DeleteDefectHandler(DefectActivity defectActivity, int numberOfPressedElement) {
        this.defectActivity = defectActivity;
        this.numberOfPressedElement = numberOfPressedElement;
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Удаление");
        builder.setMessage("Вы действительно хотите удалить дефект?");
        builder.setPositiveButton("Да", (dialog, which) -> {

            // Создание  объекта DAO для работы с БД
            ReportDAO reportDAO = Bd.getAppDatabaseClass(view.getContext().getApplicationContext()).getReportDao();
            // Удаляем щит из БД
            ReportEntity reportEntity = Storage.currentReportEntityStorage;
            ArrayList<Defect> defectArrayList = reportEntity.getShields().get(Storage.currentNumberSelectedShield).getDefects();
            defectArrayList.remove(numberOfPressedElement);
            reportEntity.getShields().get(Storage.currentNumberSelectedShield).setDefects(defectArrayList);
            reportDAO.insertReport(new ReportInDB(reportEntity));

            Storage.isDeleteDefect = true;

            defectActivity.finish();
        });
        builder.setNegativeButton("Нет", (dialog, which) -> {
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
