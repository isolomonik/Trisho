package com.isolomonik.trisho.Loaders;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class PurchaseEditLoader extends AsyncTaskLoader<Boolean>{
    private PurchaseModel model;
    private Boolean response=false;
    public PurchaseEditLoader(Context context, Bundle args) {
        super(context);
        if (args!=null){
            model=(PurchaseModel)args.get("model");
        }
    }

    @Override
    public Boolean loadInBackground() {
        RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);
        Call<Boolean> call = rest.editPurchase(model,GlobalVar.API_TOKEN);
        try {
            Response resp=call.execute();
            response = (Boolean) resp.body();
            Log.v("my_log", "edit purchase" + response.toString());


        } catch (IOException e) {
            e.printStackTrace();

        }
        return response;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();

    }


}



