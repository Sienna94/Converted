package com.example.mycoronav.network

import com.example.mycoronav.vo2.Hospital
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitService {
    @GET("rprtHospService/getRprtHospService")
    fun getHospitalList(
        @QueryMap params: Map<String, String>
    ): Call<Hospital>
}