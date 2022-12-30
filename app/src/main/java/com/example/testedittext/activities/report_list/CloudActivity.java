package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.server.Server;
import com.example.testedittext.entities.ReportEntity;

import java.util.ArrayList;
import java.util.Collections;

public class CloudActivity extends AppCompatActivity implements ResponseReportListFromServerCallback {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_activity);

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        boolean authorize = sharedPreferences.getBoolean("authorize", false);
        if(!authorize) {
            Toast toast = Toast.makeText(this,
                    "Необходимо войти в аккаунт", Toast.LENGTH_SHORT);
            toast.show();
            this.finish();
        }

        TextView tvLocal =  findViewById(R.id.tvLocal);
        progressBar =  findViewById(R.id.progressBar);
        tvLocal.setOnClickListener(view -> finish());

        progressBar.setVisibility(View.VISIBLE);

        getRequest();

    }

    public void getRequest(){
        new Server().getReportEntityList("userid", this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        getRequest();
    }


    private  void setAdapter(ArrayList<ReportEntity> reportEntities){
        progressBar.setVisibility(View.GONE);
        if (recyclerView == null)  recyclerView = findViewById(R.id.cloudrv);
        Collections.reverse(reportEntities);
        // Создаем адаптер и назначаем его  recyclerView
        CloudAdapter adapter = new CloudAdapter(this, reportEntities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void callbackCall(ArrayList<ReportEntity> reportEntities) {
        setAdapter(reportEntities);
    }
}