package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.defect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.shield_list.shield.DeleteShieldHandler;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.Defect;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.Storage;
import com.example.testedittext.visual.InstantAutoComplete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefectActivity extends AppCompatActivity {
    private int numberOfPressedDefect;
    ReportEntity report;
    Defect defect;
    ArrayList<Defect> defectArrayList;
    InstantAutoComplete  defGroup, def, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defect_activity);

        // Кнопка удалить щит
        FloatingActionButton deleteShield =  findViewById(R.id.deleteDefect);
        deleteShield.setColorFilter(Color.argb(255, 255, 255, 255));

        defGroup = findViewById(R.id.defectGroup);
        def = findViewById(R.id.defect);
        note = findViewById(R.id.note);


        report = Storage.currentReportEntityStorage;
        defectArrayList = report.getShields().get(Storage.currentNumberSelectedShield).getDefects();




        // Если нажали на элемент RV, получаем индекс элемента через Intent и объект дефекта из хранилища
        // Если нажали на кнопку новый щит, он создается
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            numberOfPressedDefect = (int) arguments.get("numberOfPressedDefect");
            Storage.currentNumberOfPressedDefect = numberOfPressedDefect;

            // Если создали новый дефект, то передается его номер в обработчике AddDefectHandler, но он еще не создан в отчете, и поэтому нужно его сначала создать
            if (defectArrayList == null){
                defectArrayList = new ArrayList<>();
                defectArrayList.add(new Defect());
                report.getShields().get(Storage.currentNumberSelectedShield).setDefects(defectArrayList);

            }
            if (numberOfPressedDefect == defectArrayList.size()){
                defectArrayList.add(new Defect());
            }


            defectArrayList = report.getShields().get(Storage.currentNumberSelectedShield).getDefects();
            defect = defectArrayList.get(numberOfPressedDefect);
        }

        // Нажатие на кнопку удалить щит
        deleteShield.setOnClickListener(new DeleteDefectHandler(this, numberOfPressedDefect));

        setDataToFieldsFromBd();






        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapterDefectGroups = new ArrayAdapter (this, R.layout.custom_spinner, getDefectGroupArray());

        ArrayAdapter<String> adapterDefects = new ArrayAdapter (this,  R.layout.item_autocomplete, R.id.textViewItem, getDefectsList());

        defGroup.setAdapter(adapterDefectGroups);
        def.setAdapter(adapterDefects);
        note.setAdapter(adapterDefectGroups);

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!Storage.isDeleteDefect) {
            readDataFromFields();
            saveReport();
        }else Storage.isDeleteDefect = false;
    }

    private void saveReport() {
        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(getApplicationContext()).getReportDao();

        Storage.setDefects(defectArrayList);
        reportDAO.insertReport(new ReportInDB(Storage.currentReportEntityStorage));
    }

    private void readDataFromFields() {
        defect.setDefectGroup(defGroup.getText().toString());
        defect.setDefect(def.getText().toString());
        defect.setNote(note.getText().toString());
    }

    private void setDataToFieldsFromBd() {
        defGroup.setText(defect.getDefectGroup());
        def.setText(defect.getDefect());
        note.setText(defect.getNote());
    }

    public String[] getDefectGroupArray(){
        Map<String, List<Map<String, String>>> defectsMap = Storage.defects;
        Set<String> keySet = defectsMap.keySet();
        String[]  defectGroup = new String[keySet.size()];

        int i =0;
        for (String s :keySet) {
            defectGroup[i++] = s;
        }
        return defectGroup;
    }

    public ArrayList<String> getDefectsList(){
        Map<String, List<Map<String, String>>> defectsMap = Storage.defects;

        ArrayList<String> defectsArrayList = new ArrayList<>();
        for (Map.Entry entry :defectsMap.entrySet()) {
            ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) entry.getValue();
            for (Map<String, String> map: list){
                defectsArrayList.addAll(map.keySet());
            }
        }

        String[] defects = new String[defectsArrayList.size()];
        int i =0;
        for (String s :defectsArrayList) {
            defects[i++] = s;
        }

        return defectsArrayList;
    }

}