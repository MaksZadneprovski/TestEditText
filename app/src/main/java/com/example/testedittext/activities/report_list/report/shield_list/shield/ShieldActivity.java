package com.example.testedittext.activities.report_list.report.shield_list.shield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.GroupListActivity;
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
    TextView tvShieldGroups;
    RadioButton shieldRadio1_1,shieldRadio1_2,shieldRadio2_1,shieldRadio2_2,shieldRadio2_3 ,shieldRadio2_4;


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

        shieldName = findViewById(R.id.shieldName);
        tvShieldGroups = findViewById(R.id.tvShieldGroups);
        shieldRadio1_1 = findViewById(R.id.shieldRadio1_1);
        shieldRadio1_2 = findViewById(R.id.shieldRadio1_2);
        shieldRadio2_1 = findViewById(R.id.shieldRadio2_1);
        shieldRadio2_2 = findViewById(R.id.shieldRadio2_2);
        shieldRadio2_3 = findViewById(R.id.shieldRadio2_3);
        shieldRadio2_4 = findViewById(R.id.shieldRadio2_4);

        // Берем акуальный объект отчета из хранилища
        report = Storage.currentReportEntityStorage;
        shieldArrayList = report.getShields();


        // Нажатие на текст "Группы"
        tvShieldGroups.setOnClickListener(view -> startActivity(new Intent(view.getContext(), GroupListActivity.class)));

        // Если нажали на элемент LV, получаем индекс элемента через Intent и объект щита из хранилища
        // Иначе индекс = -1 и созаем новый щит
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            numberOfPressedShield = (int) arguments.get("numberOfPressedShield");
            Storage.currentNumberSelectedShield = numberOfPressedShield;
            shield = report.getShields().get(numberOfPressedShield);
        }else {
            numberOfPressedShield = -1;
            shield = new Shield();
        }


        // Нажатие на кнопку удалить щит
        deleteShield.setOnClickListener(new DeleteShieldHandler(this, numberOfPressedShield));

        setDataToFieldsFromBd();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Обновляем актуальный объект отчета из хранилища
        //Storage.currentReportEntityStorage = report;

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

        if (shieldArrayList == null ){
            shieldArrayList = new ArrayList<>();
            shieldArrayList.add(shield);
        }else {
            if (!shieldArrayList.isEmpty() && numberOfPressedShield != -1) {
                shieldArrayList.remove(numberOfPressedShield);
                shieldArrayList.add(numberOfPressedShield,shield);
            }else {
                shieldArrayList.add(shield);
            }

        }

        report.setShields(shieldArrayList);
        reportDAO.insertReport(new ReportInDB(report));

    }

}