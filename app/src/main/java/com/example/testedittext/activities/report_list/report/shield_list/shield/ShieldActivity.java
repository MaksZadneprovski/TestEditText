package com.example.testedittext.activities.report_list.report.shield_list.shield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.DefectListActivity;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.GroupListActivity;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.MetallicBondActivity;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.test;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.entities.enums.Phases;
import com.example.testedittext.utils.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShieldActivity extends AppCompatActivity {

    EditText shieldName;
    TextView tvShieldGroups, tvShieldDefects, tvMetallicBond;
    RadioButton shieldRadio1_1,shieldRadio1_2,shieldRadio2_1,shieldRadio2_2,shieldRadio2_3 ,shieldRadio2_4;
    ProgressBar progressBar;


    Shield shield;
    ArrayList<Shield> shieldArrayList;
    ReportEntity report;
    int numberOfPressedShield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shield_activity);

        // Кнопка удалить щит
        FloatingActionButton deleteShield =  findViewById(R.id.deleteShield);
        deleteShield.setColorFilter(Color.argb(255, 255, 255, 255));

        progressBar = findViewById(R.id.progressBar3);

        shieldName = findViewById(R.id.shieldName);
        tvShieldGroups = findViewById(R.id.tvShieldGroups);
        tvShieldDefects = findViewById(R.id.tvShieldDefects);
        tvMetallicBond = findViewById(R.id.tvShieldMetallicBond);
        shieldRadio1_1 = findViewById(R.id.shieldRadio1_1);
        shieldRadio1_2 = findViewById(R.id.shieldRadio1_2);
        shieldRadio2_1 = findViewById(R.id.shieldRadio2_1);
        shieldRadio2_2 = findViewById(R.id.shieldRadio2_2);
        shieldRadio2_3 = findViewById(R.id.shieldRadio2_3);
        shieldRadio2_4 = findViewById(R.id.shieldRadio2_4);

        // Берем акуальный объект отчета из хранилища
        report = Storage.currentReportEntityStorage;
        shieldArrayList = report.getShields();


        // Нажатие на текст
        tvShieldGroups.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            Thread thread = new Thread(() -> startActivity(new Intent(view.getContext(), GroupListActivity.class)));
            thread.start();
        });

        tvShieldDefects.setOnClickListener(view -> startActivity(new Intent(view.getContext(), DefectListActivity.class)));
        tvMetallicBond.setOnClickListener(view -> startActivity(new Intent(view.getContext(), MetallicBondActivity.class)));

        // Если нажали на элемент LV, получаем индекс элемента через Intent и объект щита из хранилища
        // Если нажали на кнопку новый щит, он создается
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            numberOfPressedShield = (int) arguments.get("numberOfPressedShield");
            Storage.currentNumberSelectedShield = numberOfPressedShield;

            if (shieldArrayList == null){
                shieldArrayList = new ArrayList<>();
                shieldArrayList.add(new Shield());
                report.setShields(shieldArrayList);
            }

            // Если создали новый щит, то передается его номер в обработчике AddShieldHandler, но он еще не создан в отчете, и поэтому нужно его сначала создать
            if (numberOfPressedShield == shieldArrayList.size()){
                shieldArrayList.add(new Shield());
            }

            shield = shieldArrayList.get(numberOfPressedShield);
        }


        // Нажатие на кнопку удалить щит
        deleteShield.setOnClickListener(new DeleteShieldHandler(this, numberOfPressedShield));

        setDataToFieldsFromBd();

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!Storage.isDeleteShield) {
            readDataFromFields();
            saveReport();
        }else Storage.isDeleteShield = false;
    }

    private void setDataToFieldsFromBd(){
        shieldName.setText(shield.getName());

        if (shield.isPEN()) shieldRadio1_2.toggle();
        else shieldRadio1_1.toggle();

        if (shield.getPhases() != null){
            switch (shield.getPhases()){
                case A : shieldRadio2_1.toggle(); break;
                case B : shieldRadio2_2.toggle(); break;
                case C : shieldRadio2_3.toggle(); break;
                case ABC : shieldRadio2_4.toggle(); break;
            }
        }

    }

    private void readDataFromFields(){
        shield.setName(shieldName.getText().toString());
        shield.setPEN(shieldRadio1_2.isChecked());
        if (shieldRadio2_1.isChecked()) shield.setPhases(Phases.A);
        if (shieldRadio2_2.isChecked()) shield.setPhases(Phases.B);
        if (shieldRadio2_3.isChecked()) shield.setPhases(Phases.C);
        if (shieldRadio2_4.isChecked()) shield.setPhases(Phases.ABC);
    }

    public void saveReport(){
        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(getApplicationContext()).getReportDao();

        if (!shieldArrayList.isEmpty()) {
            shieldArrayList.remove(numberOfPressedShield);
            shieldArrayList.add(numberOfPressedShield,shield);
        }else {
            shieldArrayList.add(shield);
        }

        report.setShields(shieldArrayList);
        Storage.currentReportEntityStorage = report;
        reportDAO.insertReport(new ReportInDB(report));

    }

}