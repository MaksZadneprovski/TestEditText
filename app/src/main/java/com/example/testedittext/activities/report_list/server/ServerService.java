package com.example.testedittext.activities.report_list.server;


import com.example.testedittext.entities.Pojo;
import com.example.testedittext.utils.Storage;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerService {

    @GET("getreports")
    Call<List<ReportPojo>> getreports( @Query("userid") String userid);

    @GET("deletereport")
    Call<ResponseBody> deletereport( @Query("userid") String userid, @Query("reportname") String reportname);

    @POST("savereport")
    Call<ResponseBody> addReport(@Query("userid") String userid,  @Body Pojo report);

    @GET("checkuser")
    Call<ResponseBody> checkUser(@Query("login") String login,  @Query("password") String password);

}

