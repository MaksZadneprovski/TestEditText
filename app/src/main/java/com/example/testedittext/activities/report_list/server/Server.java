package com.example.testedittext.activities.report_list.server;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.testedittext.activities.report_list.AuthorizeCallback;
import com.example.testedittext.activities.report_list.ResponseEfficiencyListFromServerCallback;
import com.example.testedittext.activities.report_list.ResponseReportListFromServerCallback;
import com.example.testedittext.activities.report_list.ResponseUserListFromServerCallback;
import com.example.testedittext.activities.report_list.admin.account.AccountActivity;
import com.example.testedittext.entities.Efficiency;
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

   private boolean result = false;

    private SharedPreferences sharedPreferences;
    private static final String APP_PREFERENCES = "mysettings";
    private SharedPreferences.Editor editor;
    public static final String BASE_URL = "http://194.87.214.82:8080/";
    private  List<ReportPojo> reportPojoList;
    private  List<UserPojo> userPojoList;
    private  List<Efficiency> efficiencyList;

    ResponseReportListFromServerCallback reportListFromServerCallback;
    ResponseUserListFromServerCallback userListFromServerCallback;
    ResponseEfficiencyListFromServerCallback efficiencyListFromServerCallback;
    AuthorizeCallback authorizeCallback;

    ServerService serverService;

    public  void getReportEntityList(String login, ResponseReportListFromServerCallback callback){

        this.reportListFromServerCallback = callback;
        Call<List<ReportPojo>> reportsCall = getServerService().getreports(login);

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


    public  void getUserEntityList(ResponseUserListFromServerCallback callback){

        this.userListFromServerCallback = callback;
        Call<List<UserPojo>> reportsCall = getServerService().getusers();

        reportsCall.enqueue(new Callback<List<UserPojo>>() {
            @Override
            public void onResponse(Call<List<UserPojo>> call, Response<List<UserPojo>> response) {

                userPojoList = response.body();

                userListFromServerCallback.callbackCall(userPojoList);
            }

            @Override
            public void onFailure(Call<List<UserPojo>> call, Throwable t) {
                call.cancel();
            }
        });
    }



    public  void saveReport(String userid){
        ReportEntity reportEntityStorage = Storage.currentReportEntityStorage;
        String reportString = new Gson().toJson(reportEntityStorage);
        Call<ResponseBody> call = getServerService().addReport(userid ,  new Pojo(reportEntityStorage.getName(), reportString));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {}
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public  void deleteReport(String userid, ReportEntity reportEntity){
        Call<ResponseBody> call = getServerService().deletereport(userid, reportEntity.getName());
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

        String login = sharedPreferences.getString("login", "");
        String pass = sharedPreferences.getString("pass", "");

        Call<ResponseBody> call = getServerService().checkUser(login, pass);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body()!=null){
                        String result = response.body().string();
                        if (result.equals("ok")) {
                            editor.putBoolean("authorize", true);
                            editor.apply();
                            authorizeCallback.callbackCall();
                        }
                    }
                } catch (IOException e) {e.printStackTrace();}
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


    public  boolean checkuserbylogin(String login) {
        Call<ResponseBody> call = getServerService().checkuserbylogin(login);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    if (result!=null && result.equals("ok")) {
                        setResult(true);
                    }
                } catch (IOException e) {e.printStackTrace();}
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return result;
    }

    public  boolean createUser(String login, String pass, AccountActivity accountActivity) {

        Call<ResponseBody> call = getServerService().createUser(login, pass);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    if (result.equals("saved")) {
                        accountActivity.updateList();
                    }
                } catch (IOException e) {e.printStackTrace();}
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return result;
    }

    public  boolean deleteUser(String login,AccountActivity accountActivity ) {

        Call<ResponseBody> call = getServerService().deleteUser(login);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body()!=null){
                        String s = response.body().string();
                        if (s!= null && s.equals("deleted")) {
                            result = true;
                            accountActivity.updateList();
                        }
                    }
                } catch (IOException e) {e.printStackTrace();}
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return result;
    }

    private ServerService getServerService() {
        if (serverService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            serverService = retrofit.create(ServerService.class);
        }
        return serverService;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public  void saveEfficiency(Context context, Efficiency efficiency){
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String login = sharedPreferences.getString("login", "");

        efficiency.setLogin(login);
        Call<ResponseBody> call = getServerService().saveEfficiency(efficiency);
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

    public  void getEfficiencyByLogin(ResponseEfficiencyListFromServerCallback callback, String login){

        this.efficiencyListFromServerCallback = callback;
        Call<List<Efficiency>> efficiencyCall = getServerService().getEfficiency(login);

        efficiencyCall.enqueue(new Callback<List<Efficiency>>() {
            @Override
            public void onResponse(Call<List<Efficiency>> call, Response<List<Efficiency>> response) {

                efficiencyList = response.body();

                efficiencyListFromServerCallback.callbackCall(efficiencyList);
            }

            @Override
            public void onFailure(Call<List<Efficiency>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public  void getEfficiency(ResponseEfficiencyListFromServerCallback callback){

        this.efficiencyListFromServerCallback = callback;
        Call<List<Efficiency>> efficiencyCall = getServerService().getAllEfficiency();

        efficiencyCall.enqueue(new Callback<List<Efficiency>>() {
            @Override
            public void onResponse(Call<List<Efficiency>> call, Response<List<Efficiency>> response) {

                efficiencyList = response.body();

                efficiencyListFromServerCallback.callbackCall(efficiencyList);
            }

            @Override
            public void onFailure(Call<List<Efficiency>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public  void getEfficiencyByLoginIn(ResponseEfficiencyListFromServerCallback callback, List<String> logins){

        this.efficiencyListFromServerCallback = callback;
        Call<List<Efficiency>> efficiencyCall = getServerService().getEfficiencyByLoginIn(logins);

        efficiencyCall.enqueue(new Callback<List<Efficiency>>() {
            @Override
            public void onResponse(Call<List<Efficiency>> call, Response<List<Efficiency>> response) {

                efficiencyList = response.body();

                efficiencyListFromServerCallback.callbackCall(efficiencyList);
            }

            @Override
            public void onFailure(Call<List<Efficiency>> call, Throwable t) {
                call.cancel();
            }
        });
    }

}
