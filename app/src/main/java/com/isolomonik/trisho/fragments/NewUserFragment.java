package com.isolomonik.trisho.fragments;


import android.content.Context;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.RestAPI.RESTRetrofitInterface;
import com.isolomonik.trisho.models.LoginModel;
import com.isolomonik.trisho.models.RegisterModel;
import com.isolomonik.trisho.utils.CallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewUserFragment extends Fragment implements Callback<String> {


    public NewUserFragment() {
        // Required empty public constructor
    }

    CallBackInterface callBackInterface;

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
            RESTRetrofitInterface rest = retrofit.create(RESTRetrofitInterface.class);
            Call<String> call = rest.register(registerModel);

            call.enqueue(NewUserFragment.this);

            // end // restapi
        }
    };

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        Log.v(GlobalVar.MY_LOG, "получилось" + response.body().toString());
        String responseBody = response.body().toString();

        callBackInterface.registerSubmit();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
Log.v(GlobalVar.MY_LOG, "не  получилось!!!! ");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackInterface = (CallBackInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_user, container, false);
    }


}
