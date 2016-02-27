package com.isolomonik.trisho.RestAPI;

import com.isolomonik.trisho.models.LoginModel;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.models.RegisterModel;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface RetrofitAPIInterface {

    @Headers({ "Accept: application/json",
               "Content-Type: application/json; charset=utf-8"})
    @POST("api/Login")
    Call<String> loginToken(@Body LoginModel loginModel);


    @Headers("Content-Type: application/json")
    @PUT("api/Register")
    Call<String> register(@Body RegisterModel registerModel);

    @Headers("Content-Type: application/json")
    @GET("api/Purchase")
    Call<List<PurchaseModel>> purchaseList(@QueryMap Map<String, String> parameters);
}
