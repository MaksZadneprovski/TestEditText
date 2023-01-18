package com.example.testedittext.activities.report_list.report.ground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.ground.grounding_device.GroundingDeviceActivity;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.Ground;
import com.example.testedittext.entities.GroundingDevice;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.enums.GroundingSystem;
import com.example.testedittext.utils.Storage;

public class GroundActivity extends AppCompatActivity {

    EditText seazonKoef,udelnoeR;
    RadioButton infRadio1_1, infRadio1_2, infRadio1_3, infRadio1_4, infRadio1_5, infRadio1_6, infRadio1_7;
    RadioButton infRadio2_1, infRadio2_2, infRadio2_3;
    RadioButton infRadio3_1, infRadio3_2, infRadio3_3;
    RadioButton infRadio4_1, infRadio4_2;
    RadioButton infRadio5_1, infRadio5_2, infRadio5_3;
    ReportEntity report;
    TextView tvZazemliteli;
    Ground ground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ground_activity);

        seazonKoef = findViewById(R.id.groundCoefSeazon);
        udelnoeR = findViewById(R.id.groundResistGrunt);
        infRadio1_1 = findViewById(R.id.groundRadio1_1);
        infRadio1_2 = findViewById(R.id.groundRadio1_2);
        infRadio1_3 = findViewById(R.id.groundRadio1_3);
        infRadio1_4 = findViewById(R.id.groundRadio1_4);
        infRadio1_5 = findViewById(R.id.groundRadio1_5);
        infRadio1_6 = findViewById(R.id.groundRadio1_6);
        infRadio1_7 = findViewById(R.id.groundRadio1_7);
        infRadio2_1 = findViewById(R.id.groundRadio2_1);
        infRadio2_2 = findViewById(R.id.groundRadio2_2);
        infRadio2_3 = findViewById(R.id.groundRadio2_3);
        infRadio3_1 = findViewById(R.id.groundRadio3_1);
        infRadio3_2 = findViewById(R.id.groundRadio3_2);
        infRadio3_3 = findViewById(R.id.groundRadio3_3);
        infRadio4_1 = findViewById(R.id.groundRadio4_1);
        infRadio4_2 = findViewById(R.id.groundRadio4_2);
        infRadio5_1 = findViewById(R.id.groundRadio5_1);
        infRadio5_2 = findViewById(R.id.groundRadio5_2);
        infRadio5_3 = findViewById(R.id.groundRadio5_3);
        tvZazemliteli = findViewById(R.id.tvZazemliteli);

        tvZazemliteli.setOnClickListener(view -> startActivity(new Intent(view.getContext(), GroundingDeviceActivity.class)));

        // Берем акуальный объект отчета из хранилища
        report = Storage.currentReportEntityStorage;

        setDataToFieldsFromBd();
    }

    @Override
    protected void onPause() {
        super.onPause();
        readDataFromFields();
        // Обновляем актуальный объект отчета из хранилища
        Storage.currentReportEntityStorage = report;
        saveReport();
    }


    private void setDataToFieldsFromBd() {

        ground = report.getGround();
        if (ground == null) ground = new Ground();
        seazonKoef.setText(ground.getSeazonKoef());
        udelnoeR.setText(ground.getUdelnoeR());

        String soilType = ground.getSoilType();
        if (soilType != null && !soilType.isEmpty()) {
            switch (soilType) {
                case "Суглинок":
                    infRadio1_1.toggle();
                    break;
                case "Песок":
                    infRadio1_2.toggle();
                    break;
                case "Глина":
                    infRadio1_3.toggle();
                    break;
                case "Чернозем":
                    infRadio1_4.toggle();
                    break;
                case "Торф":
                    infRadio1_5.toggle();
                    break;
                case "Садовый":
                    infRadio1_6.toggle();
                    break;
                case "Каменистый":
                    infRadio1_7.toggle();
                    break;
            }
        }

        String soilCharacter = ground.getSoilCharacter();
        if (soilCharacter != null && !soilCharacter.isEmpty()) {
            switch (soilCharacter) {
                case "Влажный":
                    infRadio2_1.toggle();
                    break;
                case "Средневлажный":
                    infRadio2_2.toggle();
                    break;
                case "Сухой":
                    infRadio2_3.toggle();
                    break;
            }
        }

        String voltage = ground.getVoltage();
        if (voltage != null && !voltage.isEmpty()) {
            switch (voltage) {
                case "До 1000 В":
                    infRadio3_1.toggle();
                    break;
                case "До и выше 1000 В":
                    infRadio3_2.toggle();
                    break;
                case "Выше 1000 В":
                    infRadio3_3.toggle();
                    break;
            }
        }

        String neutralMode = ground.getNeutralMode();
        if (neutralMode != null && !neutralMode.isEmpty()) {
            switch (neutralMode) {
                case "Глухозаземленная":
                    infRadio4_1.toggle();
                    break;
                case "Изолированная":
                    infRadio4_2.toggle();
                    break;

            }
        }

        String lightningProtectionCategory = ground.getLightningProtectionCategory();
        if (lightningProtectionCategory != null && !lightningProtectionCategory.isEmpty()) {
            switch (lightningProtectionCategory) {
                case "1":
                    infRadio5_1.toggle();
                    break;
                case "2":
                    infRadio5_2.toggle();
                    break;
                case "3":
                    infRadio5_3.toggle();
                    break;
            }
        }

    }

    private void readDataFromFields () {
        ground = report.getGround();
        if (ground == null) ground = new Ground();
        ground.setSeazonKoef(seazonKoef.getText().toString());
        ground.setUdelnoeR(udelnoeR.getText().toString());

        if (infRadio1_1.isChecked()) ground.setSoilType("Суглинок");
        else if (infRadio1_2.isChecked()) ground.setSoilType("Песок");
        else if (infRadio1_3.isChecked()) ground.setSoilType("Глина");
        else if (infRadio1_4.isChecked()) ground.setSoilType("Чернозем");
        else if (infRadio1_5.isChecked()) ground.setSoilType("Торф");
        else if (infRadio1_6.isChecked()) ground.setSoilType("Садовый");
        else if (infRadio1_7.isChecked()) ground.setSoilType("Каменистый");

        if (infRadio2_1.isChecked()) ground.setSoilCharacter("Влажный");
        else if (infRadio2_2.isChecked()) ground.setSoilCharacter("Средневлажный");
        else if (infRadio2_3.isChecked()) ground.setSoilCharacter("Сухой");

        if (infRadio3_1.isChecked()) ground.setVoltage("До 1000 В");
        else if (infRadio3_2.isChecked()) ground.setVoltage("До и выше 1000 В");
        else if (infRadio3_3.isChecked()) ground.setVoltage("Выше 1000 В");

        if (infRadio4_1.isChecked()) ground.setNeutralMode("Глухозаземленная");
        else if (infRadio4_2.isChecked()) ground.setNeutralMode("Изолированная");

        if (infRadio5_1.isChecked()) ground.setLightningProtectionCategory("1");
        else if (infRadio5_2.isChecked()) ground.setLightningProtectionCategory("2");
        else if (infRadio5_3.isChecked()) ground.setLightningProtectionCategory("3");

        report.setGround(ground);
    }

    public void saveReport() {
        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(getApplicationContext()).getReportDao();
        reportDAO.insertReport(new ReportInDB(report));
    }
}