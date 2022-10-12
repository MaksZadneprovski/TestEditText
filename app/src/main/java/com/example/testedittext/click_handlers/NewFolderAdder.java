package com.example.testedittext.click_handlers;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.activities.FolderListActivity;
import com.example.testedittext.adapters.FilesListViewAdapter;
import com.example.testedittext.utils.DirectoryUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class NewFolderAdder implements View.OnClickListener{

    FolderListActivity folderListActivity;
    ArrayList<File> folderList;
    ListView listView;

    public NewFolderAdder(FolderListActivity folderListActivity, ArrayList<File> folderList, ListView listView) {
        this.folderListActivity = folderListActivity;
        this.folderList = folderList;
        this.listView = listView;
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();

        // Ввод названия папки
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Создать папку");
        alert.setMessage("Введите название");
        final EditText input = new EditText(context);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = String.valueOf(input.getText());
                Path path = Paths.get(view.getContext().getExternalFilesDir(null) + "/" + value);

                if (!Files.exists(path)) {
                    try {
                        Files.createDirectory(path);
                        // Нужно обновить адаптер
                        folderList = DirectoryUtil.getFolderAndFileList(context.getExternalFilesDir(null).toString());
                        FilesListViewAdapter newAdapter = new FilesListViewAdapter(context, DirectoryUtil.getFolderAndFileList(context.getExternalFilesDir(null).toString()));
                        listView.setAdapter(newAdapter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast toast = Toast.makeText(view.getContext(), "Папка с таким названием уже существует!",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
        ////////////////



    }
}
