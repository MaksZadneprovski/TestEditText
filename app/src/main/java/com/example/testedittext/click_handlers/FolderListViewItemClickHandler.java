package com.example.testedittext.click_handlers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.activities.FolderListActivity;
import com.example.testedittext.activities.ReportListActivity;
import com.example.testedittext.adapters.FilesListViewAdapter;
import com.example.testedittext.utils.DirectoryUtil;

import java.io.File;
import java.util.ArrayList;

public class FolderListViewItemClickHandler implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    FolderListActivity folderListActivity;
    ArrayList <File> folderList;
    ListView listView;

    public FolderListViewItemClickHandler(FolderListActivity folderListActivity, ListView listView) {
        this.folderListActivity = folderListActivity;
        this.listView = listView;
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Context context = view.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Удаление");
        builder.setMessage("Вы действительно хотите удалить папку?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                folderList = DirectoryUtil.getFolderAndFileList(context.getExternalFilesDir(null).toString());
                File folder = folderList.get(i);
                DirectoryUtil.deleteDirectory(folder);
                folderList = DirectoryUtil.getFolderAndFileList(context.getExternalFilesDir(null).toString());
                FilesListViewAdapter newAdapter = new FilesListViewAdapter(context, DirectoryUtil.getFolderAndFileList(context.getExternalFilesDir(null).toString()));
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Context context = view.getContext();
        folderList = DirectoryUtil.getFolderAndFileList(context.getExternalFilesDir(null).toString());
        File folder = folderList.get(i);
        String path = folder.getAbsolutePath();
        Intent intent = new Intent(folderListActivity, ReportListActivity.class);
        intent.putExtra("path", path);
        folderListActivity.startActivity(intent);
    }
}
