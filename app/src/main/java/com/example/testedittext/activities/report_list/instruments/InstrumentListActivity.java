package com.example.testedittext.activities.report_list.instruments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.AddDefectHandler;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.DefectListRVAdapter;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.InstrumentDAO;
import com.example.testedittext.entities.InstrumentInDB;
import com.example.testedittext.utils.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InstrumentListActivity extends AppCompatActivity {
    List<InstrumentInDB> instrumentInDBArrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instrument_list_activity);

        // Кнопка Создать новый щит
        FloatingActionButton button =  findViewById(R.id.addInstrument);
        button.setColorFilter(Color.argb(255, 255, 255, 255));

        setAdapter();

        // Обработчик нажатия кнопки добавить щит
        button.setOnClickListener(new AddInstrumentHandler());
    }
    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
    }

    private void setAdapter(){
        InstrumentDAO instrumentDAO = Bd.getAppDatabaseClass(getApplicationContext()).getInstrumentDao();
        instrumentInDBArrayList = instrumentDAO.getAllInstruments();
        if (recyclerView == null)  recyclerView = findViewById(R.id.defect_rv);
        // Создаем адаптер и назначаем его  recyclerView
        InstrumentListRVAdapter adapter = new InstrumentListRVAdapter(instrumentInDBArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}