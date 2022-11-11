package com.example.testedittext.activities.report_list.report;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.core.content.FileProvider;

import com.example.testedittext.BuildConfig;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.report_creator.Report;
import com.example.testedittext.utils.DirectoryUtil;
import com.example.testedittext.utils.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ShareReportHandler implements View.OnClickListener {
    Context context;
    private String fileName;


    @Override
    public void onClick(View view) {

        Report r = new Report(view.getContext(), DirectoryUtil.getCurrentFolder() + ".xls", Storage.currentReportEntityStorage);
        try {
            r.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Context context = view.getContext();
        //File file = new File(DirectoryUtil.currentDirectory +"/" + "notExist");

        File file = new File(context.getExternalFilesDir(null)+ "/" + DirectoryUtil.getCurrentFolder() + ".xls");

        //Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);


        if (file != null || file.exists()){
            // Поделиться файлом
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file);
            share.putExtra(Intent.EXTRA_STREAM, contentUri);
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            share.setType("text/plain");
            context.startActivity(share);



        }
    }



}
