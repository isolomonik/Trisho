package com.isolomonik.trisho.RestAPI;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface RESTRetrofitInterface {
    @Headers("Content-Type: application/json")
    @POST("/api/Login")
    Call<String> loginToken();
}
