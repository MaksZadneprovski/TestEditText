package com.example.testedittext.activities.report_list.report;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.core.content.FileProvider;

import com.example.testedittext.BuildConfig;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.DirectoryUtil;

import java.io.File;

public class ShareReportHandler implements View.OnClickListener {
    Context context;
    private String fileName;


    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        File file = new File(DirectoryUtil.currentDirectory +"/" + "notExist");
        if (file.exists()){
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file));
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            share.setType("text/plain");
            context.startActivity(share);

        }
    }



}