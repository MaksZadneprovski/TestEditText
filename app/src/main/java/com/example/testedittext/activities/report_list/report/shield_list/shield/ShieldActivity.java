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
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.GroupListActivity2;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.MetallicBondActivity;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.test;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.entities.enums.Phases;
import com.example.testedittext.utils.Storage;
import com.example.testedittext.utils.ViewEditor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShieldActivity extends AppCompatActivity {

    EditText shieldName;
    TextView tvShieldGroups, tvShieldDefects, tvMetallicBond, tvf1,tvf2,tvf3;
    RadioButton shieldRadio1_1,shieldRadio1_2,shieldRadio2_1,shieldRadio2_2,shieldRadio2_3 ,shieldRadio2_4;
    ProgressBar progressBar;
    List<View> viewList;
    private boolean isBtnHide = true;
    View dimView;
    Shield shield;
    ArrayList<Shield> shieldArrayList;
    ReportEntity report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shield_activity);

        // Кнопка удалить щит
        FloatingActionButton deleteShield =  findViewById(R.id.deleteShield);
        FloatingActionButton copyShield =  findViewById(R.id.copyShield);
        FloatingActionButton replaceShield =  findViewById(R.id.replaceShield);
        FloatingActionButton showButton =  findViewById(R.id.showButton);
        deleteShield.setColorFilter(Color.argb(255, 255, 255, 255));
        copyShield.setColorFilter(Color.argb(255, 255, 255, 255));
        replaceShield.setColorFilter(Color.argb(255, 255, 255, 255));
        showButton.setColorFilter(Color.argb(255, 255, 255, 255));

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
        dimView = findViewById(R.id.dimView);
        tvf1 = findViewById(R.id.tvf1);
        tvf2 = findViewById(R.id.tvf2);
        tvf3 = findViewById(R.id.tvf3);

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
            Storage.currentNumberSelectedShield = (int) arguments.get("numberOfPressedShield");

            if (shieldArrayList == null){
                shieldArrayList = new ArrayList<>();
                shieldArrayList.add(new Shield());
                report.setShields(shieldArrayList);
            }

            // Если создали новый щит, то передается его номер в обработчике AddShieldHandler, но он еще не создан в отчете, и поэтому нужно его сначала создать
            if (Storage.currentNumberSelectedShield == shieldArrayList.size()){
                shieldArrayList.add(new Shield());
            }

            shield = shieldArrayList.get(Storage.currentNumberSelectedShield);
        }


        // Нажатие на кнопку удалить щит
        deleteShield.setOnClickListener(new DeleteShieldHandler(this, Storage.currentNumberSelectedShield));
        copyShield.setOnClickListener(new CopyShieldHandler(shieldArrayList, shield));
        replaceShield.setOnClickListener(new ReplaceShieldHandler(shieldArrayList, this));

        dimView.setOnClickListener(view -> setVisibilityButtons());
        showButton.setOnClickListener(view -> setVisibilityButtons());

        viewList = new ArrayList<>(Arrays.asList(deleteShield, copyShield,replaceShield, tvf1,tvf2,tvf3));

        setDataToFieldsFromBd();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Берем акуальный объект отчета из хранилища
        report = Storage.currentReportEntityStorage;
        shieldArrayList = report.getShields();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Берем акуальный объект отчета из хранилища
        report = Storage.currentReportEntityStorage;
        shieldArrayList = report.getShields();
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
        // Берем акуальный объект отчета из хранилища
        report = Storage.currentReportEntityStorage;
        shieldArrayList = report.getShields();

        if (!shieldArrayList.isEmpty()) {
            shieldArrayList.remove(Storage.currentNumberSelectedShield);
            shieldArrayList.add(Storage.currentNumberSelectedShield,shield);
        }else {
            shieldArrayList.add(shield);
        }

        report.setShields(shieldArrayList);
        Storage.currentReportEntityStorage = report;
        reportDAO.insertReport(new ReportInDB(report));

    }

    public void setVisibilityButtons(){
        if (isBtnHide) {
            ViewEditor.showButtons(viewList, dimView) ;
            isBtnHide = false;
        }
        else{
            ViewEditor.hideButtons(viewList, dimView);
            isBtnHide = true;
        }
    }

}