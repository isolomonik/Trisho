package com.isolomonik.trisho.Loaders;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class PurchaseNewLoader extends AsyncTaskLoader<String[]> {

    private String name = "";
    private String[] guid;

    public PurchaseNewLoader(Context context, Bundle args) {

        super(context);
        if (args != null) {
            name = args.getString("name");
        }
    }

    @Override
    public String[] loadInBackground() {

        RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);
        PurchaseModel model = new PurchaseModel();
        model.setName(name);
//        Map<String, String> parameters = new HashMap<String, String>();
//        parameters.put("name", name);
//        parameters.put("token", GlobalVar.API_TOKEN);
        Call<String[]> call = rest.newPurchase(model,GlobalVar.API_TOKEN);
        try {
            Response resp=call.execute();
            guid = (String[]) resp.body();
            if (guid != null) {
                Log.v("my_log", "loaded size:" + guid.length);
                //onSuccess();
            } else {
                // onError();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        return guid;


    }

    @Override
    protected void onStartLoading() {

        forceLoad();

    }
}
