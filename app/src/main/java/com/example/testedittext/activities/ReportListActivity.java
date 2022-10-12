package com.example.testedittext.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.testedittext.R;
import com.example.testedittext.click_handlers.NewFolderAdder;
import com.example.testedittext.click_handlers.NewReportAdder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReportListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_list_activity);

        FloatingActionButton buttonAddNewReport =  findViewById(R.id.addNewReport);
        NewReportAdder newReportAdder = new NewReportAdder();
        buttonAddNewReport.setOnClickListener(newReportAdder);
    }
}