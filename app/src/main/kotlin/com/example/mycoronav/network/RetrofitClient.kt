package com.example.mycoronav.network

import android.util.Log
import com.example.mycoronav.common.Constants
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {
    fun getInstance(): Retrofit {
        val logger = HttpLoggingInterceptor {
            Log.d("API", it)
        }
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(TikXmlConverterFactory.create(parser))
            .build()
    }
}