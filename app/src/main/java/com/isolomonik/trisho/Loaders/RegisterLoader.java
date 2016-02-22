package com.isolomonik.trisho.Loaders;

import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.RegisterModel;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterLoader extends AsyncTaskLoader<String> {

    private String name;
    private String telephone;
    private String email;
    private String password;



    public RegisterLoader(Context context, Bundle args ) {
        super(context);
        Log.d(GlobalVar.MY_LOG, hashCode() + " create RegisterAsyncLoader");
        if (args != null) {
            name=args.getString("Name");
            email=args.getString("Email");
            telephone = args.getString("Telephone");
            password=args.getString("Password");
        }
    }

    @Override
    public String loadInBackground() {

        Log.d(GlobalVar.MY_LOG, hashCode() + " loadInBackground start");

        //  todo restapi
        RegisterModel registerModel = new RegisterModel();
        registerModel.setTelephone("0636994483");
        registerModel.setName("Lexon");
        registerModel.setEmail("alex-pp@ukr.net");
        registerModel.setPassword("123456");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalVar.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        RetrofitAPIInterface rest = retrofit.create(RetrofitAPIInterface.class);
        Call<String> call=rest.register(registerModel);
        try {
            Response<String> response = call.execute();
            Log.v(GlobalVar.MY_LOG, "получилось"+response.body().toString());
            String responseBody=response.body().toString();
        }catch (IOException e){
            e.printStackTrace();
            Log.v(GlobalVar.MY_LOG, "не получилось");
        }

        // end // TODO: 14.02.16

        return null;
    }
}
