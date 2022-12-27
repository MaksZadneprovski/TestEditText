package com.example.testedittext.activities.report_list.server;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerService {

    @GET("getreports")
    Call<List<ReportPojo>> getreports( @Query("userid") long userid);

}

