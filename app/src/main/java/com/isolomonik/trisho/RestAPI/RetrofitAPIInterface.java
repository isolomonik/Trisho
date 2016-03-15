package com.isolomonik.trisho.RestAPI;

import com.isolomonik.trisho.models.LoginModel;
import com.isolomonik.trisho.models.PurchaseItemModel;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.models.RecomendedProductModel;
import com.isolomonik.trisho.models.RegisterModel;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Call<LoginModel> loginToken(@Body LoginModel loginModel);


    @Headers("Content-Type: application/json")
    @PUT("api/Register")
    Call<String> register(@Body RegisterModel registerModel);

    @Headers("Content-Type: application/json")
    @GET("api/Purchase")
    Call<List<PurchaseModel>> purchaseList(@QueryMap Map<String, String> parameters);

    @Headers("Content-Type: application/json")
    @GET("api/PurchaseItem")
    Call<List<PurchaseItemModel>> purchaseItems(@QueryMap Map<String, String> parameters);

    @Headers("Content-Type: application/json")
    @GET("api/RecommendedProducts")
    Call<List<RecomendedProductModel>> recommendedProducts(@QueryMap Map<String, String> parameters);

    @Headers("Content-Type: application/json")
    @GET("api/PurchaseNames")
    Call<String[]> purchaseNames(@QueryMap Map<String, String> parameters);

    @Headers("Content-Type: application/json")
    @GET("api/ProductNames")
    Call<String[]> productsNames(@QueryMap Map<String, String> parameters);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @PUT("api/Purchase")
    Call<String[]> newPurchase(@Body PurchaseModel purchaseModel, @Query ("token") String token);

    @Headers({"Content-Type: application/json; charset=utf-8"})
    @POST("api/Purchase")
    Call<Boolean> editPurchase(@Body PurchaseModel purchaseModel, @Query ("token") String token);

    @DELETE("api/Login")
    Call<String> logout (@Query ("token") String token);
}
