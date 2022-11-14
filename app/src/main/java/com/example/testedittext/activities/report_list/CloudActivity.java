package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.testedittext.R;

public class CloudActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_activity);

        TextView tvLocal =  findViewById(R.id.tvLocal);
        tvLocal.setOnClickListener(view -> finish());

    }
}