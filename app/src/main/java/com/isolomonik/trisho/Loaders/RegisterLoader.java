package com.isolomonik.trisho.Loaders;

import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.RegisterModel;
import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterLoader extends AsyncTaskLoader<String> {

    private String name;
    private String telephone;
    private String email;
    private String password;
    private String token;


    public RegisterLoader(Context context, Bundle args) {
        super(context);
        Log.d(GlobalVar.MY_LOG, hashCode() + " create RegisterAsyncLoader");
        if (args != null) {
            name = args.getString("Name");
            email = args.getString("Email");
            telephone = args.getString("Telephone");
            password = args.getString("Password");
        }
    }

    @Override
    public String loadInBackground() {

        Log.d(GlobalVar.MY_LOG, hashCode() + " register load start");


        MediaType mt = MediaType.parse("application/json; charset=utf-8");
        try {
            JSONObject json = new JSONObject();
            json.put("Telephone", telephone);
            json.put("Password", password);
            json.put("name", name);
            json.put("email", email);


            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mt, json.toString());
            Request request = new Request.Builder()
                    .url(GlobalVar.URL_API + "api/Register")
                    .put(body)
                    .build();
            okhttp3.Response response = client.newCall(request)
                    .execute();

            String resp = response.body().string();
            token = resp.replaceAll("\"", "");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }


    @Override
    protected void onStartLoading() {

            forceLoad();

    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }


    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();


    }
}