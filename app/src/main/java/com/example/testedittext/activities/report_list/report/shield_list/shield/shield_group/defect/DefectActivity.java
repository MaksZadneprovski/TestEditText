package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.defect;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.shield_list.shield.DeleteShieldHandler;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.Defect;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DefectActivity extends AppCompatActivity {
    private int numberOfPressedDefect;
    ReportEntity report;
    Defect defect;
    ArrayList<Defect> defectArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defect_activity);

        // Кнопка удалить щит
        FloatingActionButton deleteShield =  findViewById(R.id.deleteDefect);
        deleteShield.setColorFilter(Color.argb(255, 255, 255, 255));

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

    }

    private void setDataToFieldsFromBd() {

    }
}