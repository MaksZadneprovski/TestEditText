package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.shield_list.ShieldListRVAdapter;
import com.example.testedittext.entities.Defect;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DefectListActivity extends AppCompatActivity {

    ArrayList<Defect> defectList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defect_list_activity);

        // Кнопка Создать новый щит
        FloatingActionButton buttonAddDefect =  findViewById(R.id.addDefect);
        buttonAddDefect.setColorFilter(Color.argb(255, 255, 255, 255));

        setAdapter();

        // Обработчик нажатия кнопки добавить щит
        buttonAddDefect.setOnClickListener(new AddDefectHandler());
    }
    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
    }

    private void setAdapter(){
        // Обновляем список щитов, которые есть в отчете
        defectList = Storage.currentReportEntityStorage.getShields().get(Storage.currentNumberSelectedShield).getDefects();
        if (recyclerView == null)  recyclerView = findViewById(R.id.defect_rv);
        // Создаем адаптер и назначаем его  recyclerView
        DefectListRVAdapter adapter = new DefectListRVAdapter(defectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}