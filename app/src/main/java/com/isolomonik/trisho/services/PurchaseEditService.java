package com.isolomonik.trisho.services;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class PurchaseEditService extends IntentService {

    public PurchaseEditService() {
        super("PurchaseEdit");
    }

    public void onCreate() {
        super.onCreate();
        Log.d(GlobalVar.MY_LOG, "create service PurchaseEdit");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

//
//        Log.d(GlobalVar.MY_LOG, "started service PurchaseEdit");
//        OkHttpClient client = new OkHttpClient();
//        MediaType type = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(type, jsonPurchaseModel.toString());
//        Request request = new Request.Builder()
//                .url(GlobalVar.URL_API + "api/Purchase?token=" + GlobalVar.API_TOKEN)
//                .post(body)
//                .build();
//try{
//        client.newCall(request).execute();
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//        Log.d(GlobalVar.MY_LOG, "onHandleIntent start: " + jsonPurchaseModel);
//
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(GlobalVar.MY_LOG, "Destroy service Purchase edit");
    }
}
