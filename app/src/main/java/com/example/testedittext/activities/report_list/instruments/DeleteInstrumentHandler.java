package com.example.testedittext.activities.report_list.instruments;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.defect.DefectActivity;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.InstrumentDAO;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.Defect;
import com.example.testedittext.entities.InstrumentInDB;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

public class DeleteInstrumentHandler implements  AdapterView.OnClickListener{

    InstrumentActivity instrumentActivity;
    InstrumentInDB instrument;
    int numberOfPressedInstrument;
    public DeleteInstrumentHandler(InstrumentActivity instrumentActivity, int numberOfPressedInstrument) {
        this.instrumentActivity = instrumentActivity;
        this.numberOfPressedInstrument = numberOfPressedInstrument;
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Удаление");
        builder.setMessage("Вы действительно хотите удалить прибор?");
        builder.setPositiveButton("Да", (dialog, which) -> {

            // Создание  объекта DAO для работы с БД
            InstrumentDAO instrumentDAO = Bd.getAppDatabaseClass(view.getContext().getApplicationContext()).getInstrumentDao();
            if (Storage.instrumentInDBList != null && numberOfPressedInstrument < Storage.instrumentInDBList.size()) {
                instrument = Storage.instrumentInDBList.get(numberOfPressedInstrument);
                instrumentDAO.deleteInstrument(instrument);
                Storage.isDeleteInstrument = true;
            }
            instrumentActivity.finish();
        });
        builder.setNegativeButton("Нет", (dialog, which) -> {
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
