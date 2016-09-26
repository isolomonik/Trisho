package com.isolomonik.trisho.Loaders;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.RecomendedProductModel;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class RecommendedProductsLoader extends AsyncTaskLoader<List<RecomendedProductModel>> {
    private List<RecomendedProductModel> recommendedItems;
    private String guid;

    public RecommendedProductsLoader(Context context, Bundle args) {
        super(context);
        guid=args.getString("guid");
    }

    @Override
    public List<RecomendedProductModel> loadInBackground() {
        RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("purchaseGuid", guid);
        parameters.put("take", "300");
        parameters.put("skip", "0");
        parameters.put("token", GlobalVar.API_TOKEN);
        Call<List<RecomendedProductModel>> call = rest.recommendedProducts(parameters);
        try{
            recommendedItems = call.execute().body();
            Log.v("my_log", "loaded recommended size:" + recommendedItems.size());

        }catch (IOException e){
            e.printStackTrace();
        }

        return recommendedItems;

    }

    @Override
    protected void onStartLoading() {

        forceLoad();

    }
}
