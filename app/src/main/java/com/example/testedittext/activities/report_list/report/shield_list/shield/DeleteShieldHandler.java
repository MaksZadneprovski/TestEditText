package com.example.testedittext.activities.report_list.report.shield_list.shield;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

public class DeleteShieldHandler implements  AdapterView.OnClickListener{
    ShieldActivity shieldActivity;
    int numberOfPressedElement;

    public DeleteShieldHandler(ShieldActivity shieldActivity, int numberOfPressedElement) {
        this.shieldActivity = shieldActivity;
        this.numberOfPressedElement = numberOfPressedElement;
    }

    @Override
    public void onClick(View view) {
        if (numberOfPressedElement != -1) {
            Context context = view.getContext();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Удаление");
            builder.setMessage("Вы действительно хотите удалить щит?");
            builder.setPositiveButton("Да", (dialog, which) -> {

                // Создание  объекта DAO для работы с БД
                ReportDAO reportDAO = Bd.getAppDatabaseClass(view.getContext().getApplicationContext()).getReportDao();
                // Удаляем щит из БД
                ReportEntity reportEntity = Storage.currentReportEntityStorage;
                ArrayList<Shield> shieldArrayList = reportEntity.getShields();
                shieldArrayList.remove(numberOfPressedElement);
                reportEntity.setShields(shieldArrayList);
                reportDAO.insertReport(new ReportInDB(reportEntity));

                Storage.isDeleteShield = true;

                shieldActivity.finish();
            });
            builder.setNegativeButton("Нет", (dialog, which) -> {
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
