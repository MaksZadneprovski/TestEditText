package com.example.testedittext.click_handlers;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.adapters.ReportListViewAdapter;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.DirectoryUtil;

import java.io.File;
import java.util.ArrayList;

public class ReportListViewItemClickHandler implements AdapterView.OnItemLongClickListener {
    ArrayList<ReportEntity> reportEntityList;
    ListView listView;

    public ReportListViewItemClickHandler(ListView listView, ArrayList<ReportEntity> reportEntityList) {
        this.reportEntityList = reportEntityList;
        this.listView = listView;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Context context = view.getContext();
        ReportEntity report =  reportEntityList.get(i);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Удаление");
        builder.setMessage("Вы действительно хотите удалить папку?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                File file = new File(context.getExternalFilesDir(null), report.getReportName());
                DirectoryUtil.deleteDirectory(file);
                reportEntityList = DirectoryUtil.getReportEntityList(context);
                ReportListViewAdapter newAdapter = new ReportListViewAdapter(context, DirectoryUtil.getReportEntityList(context));
                listView.setAdapter(newAdapter);
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();



        return true;
    }
}
