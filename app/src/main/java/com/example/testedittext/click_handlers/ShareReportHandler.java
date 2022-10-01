package com.example.testedittext.click_handlers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.core.content.FileProvider;

import com.example.testedittext.BuildConfig;

import java.io.File;

public class ShareReportHandler implements View.OnClickListener {
    Context context;
    private String fileName = "yoyoyo.xls";

    public ShareReportHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        //String path = context.getExternalFilesDir(null).getAbsolutePath().toString()+"/yoyoyo.xls";
        File file = getExternalPath();
        if (file.exists()){
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file));
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            share.setType("text/plain");
            context.startActivity(share);
        }
    }

    private File getExternalPath() {
        return new File(context.getExternalFilesDir(null), fileName);
    }
}
