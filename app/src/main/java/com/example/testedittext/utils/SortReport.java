package com.example.testedittext.utils;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.ReportListActivity;
import com.example.testedittext.activities.report_list.ReportListRVAdapter;
import com.example.testedittext.activities.report_list.report.ReportActivity;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportInDB;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SortReport implements View.OnClickListener{

    ReportListActivity reportListActivity;
    RecyclerView recyclerView;
    ArrayList<ReportInDB> reportList;

    public SortReport(ReportListActivity reportListActivity, RecyclerView recyclerView) {
        this.reportListActivity = reportListActivity;
        this.recyclerView = recyclerView;
        this.reportList = reportList;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        if (recyclerView != null) {

            // Создание  объекта DAO для работы с БД
            ReportDAO reportDAO = Bd.getAppDatabaseClass(view.getContext()).getReportDao();
            reportList = (ArrayList<ReportInDB>) reportDAO.getAllReports();

            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_add_report, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.newR:
                            reportList = (ArrayList<ReportInDB>) reportList.stream().sorted(Comparator.comparingLong(ReportInDB::getDateOfCreate)).collect(Collectors.toList());
                            break;
                        case R.id.oldR:
                            reportList = (ArrayList<ReportInDB>) reportList.stream().sorted(Comparator.comparingLong(ReportInDB::getDateOfCreate).reversed()).collect(Collectors.toList());
                            break;
                        case R.id.az:
                            reportList = (ArrayList<ReportInDB>) reportList.stream().sorted(Comparator.comparing(ReportInDB::getName)).collect(Collectors.toList());
                            break;
                        case R.id.za:
                            reportList = (ArrayList<ReportInDB>) reportList.stream().sorted(Comparator.comparing(ReportInDB::getName).reversed()).collect(Collectors.toList());
                            break;

                    }
                    // Создаем адаптер и назначаем его  recyclerView
                    reportListActivity.changeAdapter(reportList);
                    return true;
                }
            });
            popupMenu.show();
        }
    }
}
