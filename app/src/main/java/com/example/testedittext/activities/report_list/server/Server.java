package com.example.testedittext.activities.report_list.server;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.testedittext.activities.report_list.AuthorizeCallback;
import com.example.testedittext.activities.report_list.ResponseReportListFromServerCallback;
import com.example.testedittext.entities.Pojo;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.Storage;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Server {


    private SharedPreferences sharedPreferences;
    private static final String APP_PREFERENCES = "mysettings";
    private SharedPreferences.Editor editor;
    public static final String BASE_URL = "http://195.133.196.115:8080/";
    private  List<ReportPojo> reportPojoList;
    ResponseReportListFromServerCallback reportListFromServerCallback;
    AuthorizeCallback authorizeCallback;

    public  void getReportEntityList(String userid, ResponseReportListFromServerCallback callback){
        this.reportListFromServerCallback = callback;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServerService serverService = retrofit.create(ServerService.class);

        Call<List<ReportPojo>> reportsCall = serverService.getreports(userid);



        reportsCall.enqueue(new Callback<List<ReportPojo>>() {
            @Override
            public void onResponse(Call<List<ReportPojo>> call, Response<List<ReportPojo>> response) {
                ArrayList<ReportEntity> reportEntities = new ArrayList<>();

                reportPojoList = response.body();
                for (ReportPojo p : reportPojoList) {
                    ReportEntity reportEntity = new Gson().fromJson(p.getReport(), ReportEntity.class);
                    reportEntities.add(reportEntity);
                }

                reportListFromServerCallback.callbackCall(reportEntities);

            }
            @Override
            public void onFailure(Call<List<ReportPojo>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public  void saveReport(String userid){
        ReportEntity reportEntityStorage = Storage.currentReportEntityStorage;
        String reportString = new Gson().toJson(reportEntityStorage);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServerService serverService = retrofit.create(ServerService.class);
        Call<ResponseBody> call = serverService.addReport(userid ,  new Pojo(reportEntityStorage.getName(), reportString));

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public  void deleteReport(String userid, ReportEntity reportEntity){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServerService serverService = retrofit.create(ServerService.class);
        Call<ResponseBody> call = serverService.deletereport(userid, reportEntity.getName());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {}
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {call.cancel();}
        });
    }

    public  void authorize(Context context,  AuthorizeCallback callback){
        this.authorizeCallback = callback;

        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String login = sharedPreferences.getString("login", "null");
        String pass = sharedPreferences.getString("pass", "null");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServerService serverService = retrofit.create(ServerService.class);

        Call<ResponseBody> call = serverService.checkUser(login, pass);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    if (result.equals("ok")) {
                        editor.putBoolean("authorize", true);
                        editor.apply();
                        authorizeCallback.callbackCall();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


}
