package com.isolomonik.trisho.Loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import retrofit2.Call;

public class LogoutLoader extends AsyncTaskLoader<String> {

    public LogoutLoader(Context context) {
        super(context);
    }
String token;
    @Override
    public String loadInBackground() {

        try{
            RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);

            Call<String> call = rest.logout(GlobalVar.API_TOKEN);

            token = call.execute().body();

        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v("my_log", "logout " + token);
        return token;

    }
}
