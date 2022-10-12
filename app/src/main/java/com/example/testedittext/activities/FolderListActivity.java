package com.example.testedittext.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.testedittext.R;
import com.example.testedittext.adapters.FilesListViewAdapter;
import com.example.testedittext.click_handlers.FolderListViewItemClickHandler;
import com.example.testedittext.click_handlers.NewFolderAdder;
import com.example.testedittext.utils.DirectoryUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class FolderListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_list_activity);

        //ReportRecyclerViewItemClickHandler itemClickHandler = new ReportRecyclerViewItemClickHandler();
        //RecyclerView recyclerView = findViewById(R.id.recyclerViewReportsList);
        //ReportEntityAdapter adapter = new ReportEntityAdapter(this, DirectoryUtil.getReportEntityList(this), itemClickHandler);
        //recyclerView.setAdapter(adapter);
        //recyclerView.invalidate();

        ArrayList <File> folderList =  DirectoryUtil.getFolderAndFileList(this.getExternalFilesDir(null).toString());

        FloatingActionButton buttonAddNewFolder =  findViewById(R.id.addNewFolder);
        ListView listView = findViewById(R.id.folderListView);
        NewFolderAdder newFolderAdder = new NewFolderAdder(this, folderList, listView);
        buttonAddNewFolder.setOnClickListener(newFolderAdder);
        FilesListViewAdapter adapter = new FilesListViewAdapter(this, folderList);
        FolderListViewItemClickHandler handler = new FolderListViewItemClickHandler(this, listView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(handler);
        listView.setOnItemClickListener(handler);
        System.out.println(this);

    }


}