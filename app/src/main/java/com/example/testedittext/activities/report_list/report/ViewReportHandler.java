package com.example.testedittext.activities.report_list.report;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.testedittext.BuildConfig;
import com.example.testedittext.report_creator.Report;
import com.example.testedittext.utils.Storage;

import java.io.File;
import java.io.IOException;

public class ViewReportHandler implements View.OnClickListener{

    ProgressBar progressBar;

    public ViewReportHandler(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void onClick(View view) {

        Report r = new Report(view.getContext(), Storage.currentReportEntityStorage.getName() + ".xlsx", Storage.currentReportEntityStorage);

        if (r.isBasicInfNorm()){
            if (r.isDataFromSettingsNorm()){
                progressBar.setVisibility(View.VISIBLE);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            r.generateReport();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // Открыть файл
                        Context context = view.getContext();
                        File file = new File(context.getExternalFilesDir(null)+ "/" + Storage.currentReportEntityStorage.getName() + ".xlsx");
                        //File file = new File(getExternalPath() + ".pdf");

                        Intent share = new Intent();
                        share.setAction(Intent.ACTION_VIEW);
                        Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file);
                        share.putExtra(Intent.EXTRA_STREAM, contentUri);
                        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        share.setDataAndType(contentUri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                        //share.setDataAndType(contentUri, "application/pdf");
                        //share.setType("text/plain");
                        context.startActivity(share);
                    }
                });
                thread.start();
            }else {
                Toast toast = Toast.makeText(view.getContext(), "Заполните поля в настройках приложения", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else {
            Toast toast = Toast.makeText(view.getContext(), "Заполните поля в разделе Основная информация", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Сохранять в загрузки
//    private File getExternalPath() {
//        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//        return new File(downloadsDir, Storage.currentReportEntityStorage.getName());
//    }
}
