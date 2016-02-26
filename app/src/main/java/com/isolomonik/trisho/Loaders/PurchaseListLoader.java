package com.isolomonik.trisho.Loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class PurchaseListLoader  extends AsyncTaskLoader<List<PurchaseModel>> {

    public PurchaseListLoader(Context context, Bundle args) {
        super(context);
    }

    @Override
    public List<PurchaseModel> loadInBackground() {

        OkHttpClient client = new OkHttpClient();

       // RequestBody body = RequestBody.create(JSON, login.toString());
        Request request = new Request.Builder()
                .url(GlobalVar.URL_API + "api/Purchase?take=15&skip=0&token="+GlobalVar.API_TOKEN)
                .get()
                .build();
        try {
            okhttp3.Response response = client.newCall(request)
                    .execute();
         ResponseBody resp = response.body();
        //    List<PurchaseModel>
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onStartLoading() {

            forceLoad();

    }
}
