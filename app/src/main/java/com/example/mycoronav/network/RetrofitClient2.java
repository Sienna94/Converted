package com.example.mycoronav.network;

import com.example.mycoronav.common.Constants;
import com.example.mycoronav.common.Constants2;
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
    @NotNull
    public static final RetrofitClient2 INSTANCE;
    private RetrofitClient2(){

    }
    static{
        INSTANCE = new RetrofitClient2();
    }

    public final Retrofit get(){
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.level(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logger).build();

        TikXml parser = new TikXml.Builder().exceptionOnUnreadXml(false).build();

        return new Retrofit.Builder()
                .baseUrl(Constants2.BASE_URL)
                .client(client)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build();
    }
}
