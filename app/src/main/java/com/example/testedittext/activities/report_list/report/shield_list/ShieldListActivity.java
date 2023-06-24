package com.example.testedittext.activities.report_list.report.shield_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.shield_list.shield.AddShieldHandler;
import com.example.testedittext.activities.report_list.report.shield_list.shield.ShieldActivity;
import com.example.testedittext.activities.report_list.server.Server;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.MetallicBond;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.Calculator;
import com.example.testedittext.utils.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShieldListActivity extends AppCompatActivity {

    ArrayList<Shield> shieldsList;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shield_list_activity);

        // Кнопка Создать новый щит
        FloatingActionButton buttonAddNewShield =  findViewById(R.id.addNewShield);
        buttonAddNewShield.setColorFilter(Color.argb(255, 255, 255, 255));

        setAdapter();

        // Обработчик нажатия кнопки добавить щит
        buttonAddNewShield.setOnClickListener(new AddShieldHandler());


    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();

        // Тут нужно отправить данные эффективности
        Server server = new Server();
        server.saveEfficiency(this, Calculator.getEfficiency(Storage.currentReportEntityStorage));
    }

    private void setAdapter(){
        // Обновляем список щитов, которые есть в отчете
        shieldsList = Storage.currentReportEntityStorage.getShields();
        if (recyclerView == null)  recyclerView = findViewById(R.id.shield_rv);
         // Создаем адаптер и назначаем его  recyclerView
        ShieldListRVAdapter adapter = new ShieldListRVAdapter(shieldsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}