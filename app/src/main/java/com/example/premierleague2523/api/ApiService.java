package com.example.premierleague2523.api;

import com.example.premierleague2523.data.ResponseResult;
import com.example.premierleague2523.data.ResponseSchedule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("eventsnextleague.php?id=4328")
    Call<ResponseSchedule> getSchedules();

    @GET("eventspastleague.php?id=4328")
    Call<ResponseResult> getResults();

    @GET("lookupevent.php")
    Call<ResponseSchedule> getSchedule(@Query("id") Integer id);

    @GET("lookupevent.php")
    Call<ResponseResult> getResult(@Query("id") Integer id);
}
