package com.isolomonik.trisho.RestAPI;

import android.support.annotation.NonNull;

import com.isolomonik.trisho.utils.GlobalVar;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIFactory {


    private static final OkHttpClient CLIENT = new OkHttpClient();



    @NonNull
    public static RESTRetrofitInterface getNBUExchange() {
        return getRetrofit().create(RESTRetrofitInterface.class);
    }

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(GlobalVar.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(CLIENT)
                .build();
    }
}