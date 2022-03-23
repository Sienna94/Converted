package com.example.mycoronav.network;

import com.example.mycoronav.vo2.Hospital;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RetrofitService2 {
    @GET("rprtHospService/getRprtHospService")
    Call<Hospital> getHospitalList(
            @QueryMap Map<String, String> params
    );
}
