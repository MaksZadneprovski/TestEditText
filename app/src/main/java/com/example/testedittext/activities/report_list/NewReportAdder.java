package com.example.testedittext.activities.report_list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.activities.report_list.report.ReportActivity;
import com.example.testedittext.utils.DirectoryUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

// Добавляет новую папку-отчет
public class NewReportAdder implements View.OnClickListener{

    @Override
    public void onClick(View view) {

        Context context = view.getContext();

        // Ввод названия папки
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Создать отчет");
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

                        // Устанавливаем текущую директорию
                        File file = new File(String.valueOf(path));
                        DirectoryUtil.currentDirectory = file.getAbsolutePath();;

                        Intent intent = new Intent(context, ReportActivity.class);
                        context.startActivity(intent);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast toast = Toast.makeText(view.getContext(), "Отчет с таким названием уже существует!",Toast.LENGTH_LONG);
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
