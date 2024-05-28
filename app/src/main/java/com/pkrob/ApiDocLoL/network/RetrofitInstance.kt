package com.pkrob.ApiDocLoL.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofitDDragon by lazy {
        Retrofit.Builder()
            .baseUrl("https://ddragon.leagueoflegends.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://diarabattle.tout-a-chacun.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dDragonApi: ApiService by lazy {
        retrofitDDragon.create(ApiService::class.java)
    }

    val api: ApiService by lazy {
        retrofitApi.create(ApiService::class.java)
    }
}
