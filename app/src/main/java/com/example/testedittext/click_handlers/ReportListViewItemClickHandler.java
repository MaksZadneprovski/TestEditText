package com.example.testedittext.click_handlers;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.adapters.FilesListViewAdapter;
import com.example.testedittext.utils.DirectoryUtil;

import java.io.File;
import java.util.ArrayList;

// Обработчик нажатия на отчет

public class ReportListViewItemClickHandler implements AdapterView.OnItemLongClickListener {
    ArrayList<File> fileList;
    ListView listView;
    String path;

    public ReportListViewItemClickHandler(ListView listView, ArrayList <File> fileList, String path) {
        this.fileList = fileList;
        this.listView = listView;
        this.path = path;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Context context = view.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Удаление");
        builder.setMessage("Вы действительно хотите удалить файл?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file = fileList.get(i);
                file.delete();
                fileList = DirectoryUtil.getFolderAndFileList(path);
                FilesListViewAdapter newAdapter = new FilesListViewAdapter(context, DirectoryUtil.getFolderAndFileList(path));
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