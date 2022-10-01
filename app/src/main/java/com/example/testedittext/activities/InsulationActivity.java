package com.example.testedittext.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.EditText;

import com.example.testedittext.MainActivity;
import com.example.testedittext.R;
import com.example.testedittext.click_handlers.MeasureInsulationAdder;
import com.example.testedittext.click_handlers.MeasureInsulationSaver;
import com.example.testedittext.click_handlers.ShareReportHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

// Этот активити отвечает за измерения изоляции
public class InsulationActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insulation_activity);

        List<ConstraintLayout> cLList = new ArrayList<>();

        FloatingActionButton buttonAdd =  findViewById(R.id.measureInsulationAdd);
        FloatingActionButton buttonSave =  findViewById(R.id.measureInsulationSave);
        FloatingActionButton buttonShare =  findViewById(R.id.shareReport);
        editText =  findViewById(R.id.fileName);


        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.constraint2);


        MeasureInsulationAdder measureInsulationAdder = new MeasureInsulationAdder(constraintLayout, cLList);
        MeasureInsulationSaver measureInsulationSaver = new MeasureInsulationSaver(cLList, this );
        ShareReportHandler shareReportHandler = new ShareReportHandler(InsulationActivity.this);

        buttonAdd.setOnClickListener(measureInsulationAdder);
        buttonSave.setOnClickListener(measureInsulationSaver);
        buttonShare.setOnClickListener(shareReportHandler);
    }

    public  String getFileName(){
        return editText.getText().toString();
    }
}