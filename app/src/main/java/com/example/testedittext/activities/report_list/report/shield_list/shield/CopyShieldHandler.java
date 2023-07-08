package com.example.testedittext.activities.report_list.report.shield_list.shield;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

public class CopyShieldHandler implements View.OnClickListener {
    ArrayList<Shield> shieldArrayList;
    Shield shield;

    public CopyShieldHandler(ArrayList<Shield> shieldArrayList, Shield shield) {
        this.shieldArrayList = shieldArrayList;
        this.shield = shield;
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Удаление");
        builder.setMessage("Вставить такой же щит в данный отчёт?");
        builder.setPositiveButton("Да", (dialog, which) -> {

            Shield clone = shield.clone();
            clone.setName(clone.getName() + " копия");
            shieldArrayList.add(clone);
            Toast toast = new Toast(context);
            toast.setText("Щит скопирован");
            toast.show();

        });
        builder.setNegativeButton("Нет", (dialog, which) -> {
        });
        AlertDialog dialog = builder.create();
        dialog.show();



    }
}
