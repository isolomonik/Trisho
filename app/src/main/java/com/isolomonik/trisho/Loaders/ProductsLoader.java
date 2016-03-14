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
import java.util.Map;

import retrofit2.Call;

public class ProductsLoader  extends AsyncTaskLoader<String[]> {
    String[] poductsNames;
    String name="";

    public ProductsLoader(Context context, Bundle args) {
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
        Call<String[]> call = rest.productsNames(parameters);
        try{
            poductsNames = call.execute().body();
            if (poductsNames!= null) {
                Log.v("my_log", "loaded poductsNames size:" + poductsNames.length);
                //onSuccess();
            } else {
                // onError();
            }

        }catch (IOException e){
            e.printStackTrace();

        }
        return poductsNames;


    }
    @Override
    protected void onStartLoading() {

        forceLoad();

    }
}
