package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.server.ReportPojo;
import com.example.testedittext.activities.report_list.server.ServerService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CloudActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://195.133.196.115:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_activity);

        TextView tvLocal =  findViewById(R.id.tvLocal);
        tvLocal.setOnClickListener(view -> finish());







        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServerService serverService = retrofit.create(ServerService.class);

        Call<List<ReportPojo>> reportsCall = serverService.getreports(22);

        reportsCall.enqueue(new Callback<List<ReportPojo>>() {
            @Override
            public void onResponse(Call<List<ReportPojo>> call, Response<List<ReportPojo>> response) {

                System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyeeeeeeeeeeeeeeeeeeeeeeeee");
                List<ReportPojo> reportPojoList = response.body();
                for (ReportPojo p : reportPojoList) {
                    System.out.println(p.getReport());
                }
            }

            @Override
            public void onFailure(Call<List<ReportPojo>> call, Throwable t) {
                System.out.println("nnnnnnnnnnoooooooooooooooooooooooooooo");
                call.cancel();
                System.out.println(t);
            }
        });


    }
}