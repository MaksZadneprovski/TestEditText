package com.example.testedittext.activities.report_list.instruments;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.defect.DefectActivity;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.InstrumentDAO;
import com.example.testedittext.entities.Defect;
import com.example.testedittext.entities.InstrumentInDB;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;
import java.util.List;

public class AddInstrumentHandler implements View.OnClickListener{
    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, InstrumentActivity.class);
        InstrumentDAO instrumentDAO = Bd.getAppDatabaseClass(view.getContext()).getInstrumentDao();
        List<InstrumentInDB> instrumentList =instrumentDAO.getAllInstruments();;
        if (instrumentList != null) {
            intent.putExtra("numberOfPressedInstrument", instrumentList.size());
        }else {
            intent.putExtra("numberOfPressedInstrument", 0);
        }
        context.startActivity(intent);

    }
}
