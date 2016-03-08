package com.isolomonik.trisho.Loaders;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class PurchaseNamesLoader  extends AsyncTaskLoader<String[]> {
    String[] purchaseNames;
    String name="";

    public PurchaseNamesLoader(Context context, Bundle args) {
        super(context);
        if (args!=null){
        name=args.getString("name");
        }
    }



    @Override
    public String[] loadInBackground() {

        RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("name", name);
        parameters.put("take", "50");
        parameters.put("skip", "0");
        parameters.put("token", GlobalVar.API_TOKEN);
        Call<String[]> call = rest.purchaseNames(parameters);
        try{
            purchaseNames = call.execute().body();
            if (purchaseNames!= null) {
                Log.v("my_log", "loaded size:" + purchaseNames.length);
               //onSuccess();
            } else {
               // onError();
            }

        }catch (IOException e){
            e.printStackTrace();

        }
        return purchaseNames;


         }
    @Override
    protected void onStartLoading() {

        forceLoad();

    }
}
