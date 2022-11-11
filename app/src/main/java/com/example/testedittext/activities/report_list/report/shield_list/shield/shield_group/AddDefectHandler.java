package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.testedittext.activities.report_list.report.shield_list.shield.ShieldActivity;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.defect.DefectActivity;
import com.example.testedittext.entities.Defect;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

public class AddDefectHandler implements View.OnClickListener{
    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, DefectActivity.class);
        ArrayList<Defect> defectsList = Storage.currentReportEntityStorage.getShields().get(Storage.currentNumberSelectedShield).getDefects();
        if (defectsList != null) {
            intent.putExtra("numberOfPressedDefect", defectsList.size());
        }else {
            intent.putExtra("numberOfPressedDefect", 0);
        }
        context.startActivity(intent);

    }
}