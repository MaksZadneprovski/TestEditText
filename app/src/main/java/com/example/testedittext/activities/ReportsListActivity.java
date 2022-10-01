package com.example.testedittext.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.testedittext.R;
import com.example.testedittext.adapters.ReportListViewAdapter;
import com.example.testedittext.click_handlers.ReportListViewItemClickHandler;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.DirectoryUtil;

import java.util.ArrayList;

public class ReportsListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_list_activity);

        //ReportRecyclerViewItemClickHandler itemClickHandler = new ReportRecyclerViewItemClickHandler();
        //RecyclerView recyclerView = findViewById(R.id.recyclerViewReportsList);
        //ReportEntityAdapter adapter = new ReportEntityAdapter(this, DirectoryUtil.getReportEntityList(this), itemClickHandler);
        //recyclerView.setAdapter(adapter);
        //recyclerView.invalidate();

        ArrayList<ReportEntity> reportEntityList =  DirectoryUtil.getReportEntityList(this);

        ListView listView = findViewById(R.id.reportsListView);
        ReportListViewAdapter adapter = new ReportListViewAdapter(this, reportEntityList);
        ReportListViewItemClickHandler handler = new ReportListViewItemClickHandler(listView, reportEntityList);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(handler);

    }


}