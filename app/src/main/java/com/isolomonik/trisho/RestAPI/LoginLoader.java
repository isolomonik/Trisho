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

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
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
        LoginModel loginModel = new LoginModel();
                  loginModel.setTelephone("0636994493");
            loginModel.setPassw("123456");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVar.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        RESTRetrofitInterface rest = retrofit.create(RESTRetrofitInterface.class);
        Call<String> call=rest.loginToken();
       try {
           Response<String> response = call.execute();
           Log.v(GlobalVar.MY_LOG, "получилось"+response.body().toString());
           token=response.body().toString();
       }catch (IOException e){
           e.printStackTrace();
           Log.v(GlobalVar.MY_LOG, "не получилось");
       }

       // end // TODO: 14.02.16



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



}
