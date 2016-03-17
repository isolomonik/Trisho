package com.isolomonik.trisho.Loaders;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.PurchaseItemModel;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class PurchaseItemsLoader extends AsyncTaskLoader<List<PurchaseItemModel>> {
    private List<PurchaseItemModel> purchaseItems;
    private String guid;

    public PurchaseItemsLoader(Context context, Bundle args) {
        super(context);
        guid=args.getString("guid");
    }

    @Override
    public List<PurchaseItemModel> loadInBackground() {
        RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("purchaseGuid", guid);
        parameters.put("take", "50");
        parameters.put("skip", "0");
        parameters.put("token", GlobalVar.API_TOKEN);
        Call<List<PurchaseItemModel>> call = rest.purchaseItems(parameters);
        try{
            purchaseItems = call.execute().body();
            Log.v("my_log", "loaded size:" + purchaseItems.size());
        }catch (IOException e){
            e.printStackTrace();
        }

        return purchaseItems;


    }

    @Override
    protected void onStartLoading() {

        forceLoad();

    }
}
