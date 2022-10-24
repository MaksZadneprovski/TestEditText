package com.example.testedittext.activities.report_list.report.shield_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.Reports_LV_Adapter;
import com.example.testedittext.activities.report_list.report.shield_list.shield.ShieldActivity;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.DirectoryUtil;
import com.example.testedittext.utils.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class ShieldsListActivity extends AppCompatActivity {

    ArrayList<Shield> shieldsList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shields_listactivity);

        // Кнопка Создать новый щит
        FloatingActionButton buttonAddNewShield =  findViewById(R.id.addNewShield);

        // ListView
        listView = findViewById(R.id.shieldsListView);

        // Список щитов, которые есть в отчете
        shieldsList = Storage.reportEntityStorage.getShields();

        // Обработчик нажатия кнопки добавить щит
        buttonAddNewShield.setOnClickListener(view -> startActivity(new Intent(view.getContext(), ShieldActivity.class)));

        // Обработчик нажатия LV
        listView.setOnItemClickListener(new Shields_LV_ItemClickHandler());

        // Создаем адаптер и назначаем его  listView
        Shields_LV_Adapter adapter = new Shields_LV_Adapter(this, shieldsList);
        listView.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Обновляем список щитов, которые есть в отчете
        shieldsList = Storage.reportEntityStorage.getShields();

        // Создаем адаптер и назначаем его  listView
        Shields_LV_Adapter adapter = new Shields_LV_Adapter(this, shieldsList);
        listView.setAdapter(adapter);

    }
}