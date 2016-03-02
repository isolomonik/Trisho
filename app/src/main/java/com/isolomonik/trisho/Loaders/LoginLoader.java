package com.isolomonik.trisho.Loaders;

//import android.content.AsyncTaskLoader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.LoginModel;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;


public class LoginLoader extends AsyncTaskLoader<LoginModel> {
    private String telephone;
    private String passw;
    private String token = "";
    private LoginModel user;


    public LoginLoader(Context context, Bundle args) {
        super(context);
        Log.d(GlobalVar.MY_LOG, hashCode() + " create LoginAsyncLoader");
        if (args != null) {
            telephone = args.getString("Telephone");
            passw = args.getString("Password");
        }

    }

    @Override
    public LoginModel loadInBackground() {
        Log.d(GlobalVar.MY_LOG, hashCode() + " login loading start");

        try{
        RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);
        LoginModel login = new LoginModel();
        login.setTelephone( telephone);
        login.setPassword(passw);
        Call<LoginModel> call = rest.loginToken(login);

          user = call.execute().body();

        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v("my_log", "loaded token" + user.getToken());
        return user;

       // OKHttp();
       // return token;
    }

//    @Override
//    public void deliverResult(String data) {
//        if (isReset()) {
//            token = "";
//            return;
//        }
//        if (isStarted()) {
//            super.deliverResult(data);
//        }
//    }

    @Override
    protected void onStartLoading() {

            forceLoad();

    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(LoginModel data) {
        token = "";
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        token = "";
    }

    void OKHttp() {
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        try {

            JSONObject login = new JSONObject();
            login.put("Telephone", telephone);
            login.put("Password", passw);

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, login.toString());
            Request request = new Request.Builder()
                    .url(GlobalVar.URL_API + "api/Login")
                    .post(body)
                    .build();
            okhttp3.Response response = client.newCall(request)
                    .execute();

            String resp = response.body().string();
            token = resp.replaceAll("\"", "");




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
