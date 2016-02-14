package com.isolomonik.trisho.RestAPI;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
//import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.isolomonik.trisho.models.LoginModel;
import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public class LoginLoader extends AsyncTaskLoader<String> {
    private String telephone;
    private  String passw;
    private String token="";



    public LoginLoader(Context context, Bundle args) {
        super(context);
        Log.d(GlobalVar.MY_LOG, hashCode() + " create LoginAsyncLoader");
        if (args != null) {
            telephone = args.getString("telephone");
            passw=args.getString("password");
        }

    }

    @Override
      public String loadInBackground() {
        Log.d(GlobalVar.MY_LOG, hashCode() + " loadInBackground start");
        //  todo restapi

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVar.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginRetrofit responseToken = retrofit.create(LoginRetrofit.class);

        JSONObject jsonobj = new JSONObject();
try {
    jsonobj.put("telephone", "0636994493");
    jsonobj.put("password", "123456");
}catch (JSONException e){
    e.printStackTrace();
}

        // end // TODO: 14.02.16


        token=telephone+passw;
        return token;
    }

    @Override
    public void deliverResult(String data) {
        if (isReset()) {
           token="";
            return;
        }
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (!token.equals("")) {
            deliverResult(token);
        }
        if (takeContentChanged() || token.equals("")) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(String data) {
       token="";
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

       token="";
    }

     interface LoginRetrofit {
        @POST("/api/Login")
        String responseToken(@Body LoginModel loginModel);
    }

}
