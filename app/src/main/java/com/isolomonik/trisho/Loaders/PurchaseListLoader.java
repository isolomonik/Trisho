package com.isolomonik.trisho.Loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.gson.JsonParser;
import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.utils.GlobalVar;

//import org.json.JSONObject;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class PurchaseListLoader  extends AsyncTaskLoader<List<PurchaseModel>> {

    public PurchaseListLoader(Context context, Bundle args) {
        super(context);
    }
    List<PurchaseModel> purchaseList;
    @Override
    public List<PurchaseModel> loadInBackground() {

        RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("take", "15");
        parameters.put("skip", "0");
        parameters.put("token", GlobalVar.API_TOKEN);
        Call<List<PurchaseModel>> call = rest.purchaseList(parameters);
        try{
            purchaseList = call.execute().body();

        }catch (IOException e){
            e.printStackTrace();
        }
        Log.v("my_log", "loaded size:" + purchaseList.size());
        return purchaseList;

    }
    @Override
    protected void onStartLoading() {

            forceLoad();

    }
}
