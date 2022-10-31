package com.example.testedittext.activities.report_list.report.shield_list.shield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.GroupListActivity;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShieldActivity extends AppCompatActivity {

    EditText shieldName;
    TextView tvShieldGroups;
    Shield shield;
    ArrayList<Shield> shieldArrayList;
    ReportEntity reportEntity;
    int numberOfPressedShield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shield_activity);

        // Кнопка удалить щит
        FloatingActionButton deleteShield =  findViewById(R.id.deleteShield);

        shieldName = findViewById(R.id.shieldName);
        tvShieldGroups = findViewById(R.id.tvShieldGroups);

        // Берем акуальный объект отчета из хранилища
        reportEntity = Storage.currentReportEntityStorage;
        shieldArrayList = reportEntity.getShields();


        // Нажатие на текст "Группы"
        tvShieldGroups.setOnClickListener(view -> startActivity(new Intent(view.getContext(), GroupListActivity.class)));

        // Если нажали на элемент LV, получаем индекс элемента через Intent и объект щита из хранилища
        // Иначе индекс = -1 и созаем новый щит
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            numberOfPressedShield = (int) arguments.get("numberOfPressedShield");
            Storage.currentNumberSelectedShield = numberOfPressedShield;
            shield = reportEntity.getShields().get(numberOfPressedShield);
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

        if (!Storage.isDeleteShield) {
            readDataFromFields();
            saveReport();
        }else Storage.isDeleteShield = false;
    }

    private void setDataToFieldsFromBd(){
            shieldName.setText(shield.getName());
    }

    private void readDataFromFields(){
        shield.setName(shieldName.getText().toString());
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

        reportEntity.setShields(shieldArrayList);
        reportDAO.insertReport(new ReportInDB(reportEntity));

    }

}