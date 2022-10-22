package com.example.testedittext.click_handlers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.activities.ReportListActivity;
import com.example.testedittext.activities.ReportActivity;
import com.example.testedittext.adapters.Reports_LV_Adapter;
import com.example.testedittext.utils.DirectoryUtil;

import java.io.File;
import java.util.ArrayList;

public class Reports_LV_ItemClickHandler implements  AdapterView.OnItemClickListener {


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Context context = view.getContext();
        ArrayList <File> folderList;
        folderList = DirectoryUtil.getReportList(context.getExternalFilesDir(null).toString());
        File folder = folderList.get(i);

        // Устанавливаем текущую директорию
        DirectoryUtil.currentDirectory = folder.getAbsolutePath();

        Intent intent = new Intent(context, ReportActivity.class);

        // Флаг Intent.FLAG_ACTIVITY_REORDER_TO_FRONT перемещает activity, к которой осуществляется переход на вершину стека, если она ужее есть в стеке.
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        context.startActivity(intent);
    }
}
