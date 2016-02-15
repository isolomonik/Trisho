package com.isolomonik.trisho.RestAPI;

import retrofit2.Call;
import retrofit2.http.PUT;


public interface RESTRetrofitInterface {
    @PUT("/api/Login")
    Call<String> loginToken();
}
