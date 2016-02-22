package com.isolomonik.trisho.fragments;


import android.content.Context;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.RegisterModel;
import com.isolomonik.trisho.utils.FragmentCallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONObject;

import java.util.List;

import io.realm.RealmObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewUserFragment extends Fragment implements Callback<String> {


    public NewUserFragment() {
        // Required empty public constructor
    }

    FragmentCallBackInterface fragmentCallBackInterface;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        fragmentCallBackInterface = (FragmentCallBackInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_user, container, false);
        v.findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  todo restapi
                RegisterModel registerModel = new RegisterModel();
                registerModel.setTelephone("0636994483");
                registerModel.setName("Lexon");
                registerModel.setEmail("alex-pp@ukr.net");
                registerModel.setPassword("123456");

//                RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);
//                Call<String> call = rest.register(registerModel);
//                call.enqueue(NewUserFragment.this);

                MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");
                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(JSON, registerModel.toString());
                Request request = new Request.Builder()
                        .url("http://solomon-001-site1.btempurl.com/api/Register")
                        .post(body)
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                }catch (Exception e){e.printStackTrace();}

               // token=  response.body().string();


                // end // restapi
            }
        });
        return v;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        Log.v(GlobalVar.MY_LOG, "получилось" + response.body().toString());

        fragmentCallBackInterface.registerSubmit();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Log.v(GlobalVar.MY_LOG, "не  получилось!!!! ");
    }

}
