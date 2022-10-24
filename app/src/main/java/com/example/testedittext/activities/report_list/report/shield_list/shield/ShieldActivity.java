package com.example.testedittext.activities.report_list.report.shield_list.shield;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

public class ShieldActivity extends AppCompatActivity {

    EditText shieldName;
    TextView tvShieldGroups;
    Shield shield;
    ArrayList<Shield> shieldArrayList;
    ReportEntity reportEntity;
    int numberOfPressedElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shield_activity);

        shieldName = findViewById(R.id.shieldName);
        tvShieldGroups = findViewById(R.id.tvShieldGroups);

        // Берем акуальный объект отчета из хранилища
        reportEntity = Storage.reportEntityStorage;
        shieldArrayList = reportEntity.getShields();

        // Нажатие на текст "Группы"
        //tvShieldGroups.setOnClickListener(view -> startActivity(new Intent(view.getContext(), GroupActivity.class)));

        // Если нажали на элемент LV, получаем индекс элемента через Intent и объект щита из хранилища
        // Иначе индекс = -1 и созаем новый щит
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            numberOfPressedElement = (int) arguments.get("numberOfPressedElement");
            shield = reportEntity.getShields().get(numberOfPressedElement);
        }else {
            numberOfPressedElement = -1;
            shield = new Shield();
        }



        setDataToFieldsFromBd();
    }

    @Override
    protected void onPause() {
        super.onPause();

        readDataFromFields();

        saveReport();
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
            if (!shieldArrayList.isEmpty() && numberOfPressedElement != -1) {
                shieldArrayList.remove(numberOfPressedElement);
                shieldArrayList.add(numberOfPressedElement,shield);
            }else {
                shieldArrayList.add(shield);
            }

        }

        reportEntity.setShields(shieldArrayList);
        reportDAO.insertReport(new ReportInDB(reportEntity));

    }

}