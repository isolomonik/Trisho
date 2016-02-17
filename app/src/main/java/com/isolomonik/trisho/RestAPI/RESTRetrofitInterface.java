package com.isolomonik.trisho.RestAPI;

import com.isolomonik.trisho.models.LoginModel;
import com.isolomonik.trisho.models.RegisterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface RESTRetrofitInterface {

    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("api/Login")
    Call<String> loginToken(@Body LoginModel loginModel);


    @Headers("Content-Type: application/json")
    @PUT("api/Register")
    Call<String> register(@Body RegisterModel registerModel);
}
