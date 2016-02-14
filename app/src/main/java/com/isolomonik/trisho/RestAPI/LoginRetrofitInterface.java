package com.isolomonik.trisho.RestAPI;

import retrofit2.http.PUT;

/**
 * Created by ira on 14.02.16.
 */
public interface LoginRetrofitInterface {
    @PUT("/api/Login")
    String responseToken();
}
