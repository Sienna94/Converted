package com.example.mycoronav.network;

import com.example.mycoronav.common.Constants;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public final class RetrofitClient2 {
    //한번만 메모리 할당 Static
    //한개의 객체만 사용
    //Instance
    private static RetrofitClient2 instance = new RetrofitClient2();

    //private construct
    private RetrofitClient2(){}

    public static RetrofitClient2 getInstance(){
        return instance;
    }

    public final Retrofit get(){
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.level(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logger).build();

        TikXml parser = new TikXml.Builder().exceptionOnUnreadXml(false).build();

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build();
    }
}