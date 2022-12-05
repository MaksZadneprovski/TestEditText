package com.example.testedittext.activities.report_list.report;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;

import androidx.core.content.FileProvider;

import com.example.testedittext.BuildConfig;
import com.example.testedittext.report_creator.Report;
import com.example.testedittext.utils.Storage;

import java.io.File;
import java.io.IOException;

public class ShareReportHandler implements View.OnClickListener {

    ProgressBar progressBar;

    public ShareReportHandler(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void onClick(View view) {

        progressBar.setVisibility(View.VISIBLE);

        Report r = new Report(view.getContext(), Storage.currentReportEntityStorage.getName() + ".xlsx", Storage.currentReportEntityStorage);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    r.generateReport();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Context context = view.getContext();
                File file = new File(context.getExternalFilesDir(null)+ "/" + Storage.currentReportEntityStorage.getName() + ".xlsx");

                if (file != null || file.exists()){
                    // Поделиться файлом
                    Intent share = new Intent();
                    share.setAction(Intent.ACTION_SEND);
                    Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file);
                    share.putExtra(Intent.EXTRA_STREAM, contentUri);
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    share.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    context.startActivity(share);
                }
            }
        });
        thread.start();
    }



}
