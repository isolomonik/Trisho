package com.isolomonik.trisho.services;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.PurchaseItemModel;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

public class PurchaseItemsEditService extends IntentService {

    public PurchaseItemsEditService() {

        super("PurchaseItemEdit");
    }

    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String itemGuid = intent.getStringExtra("itemGuid");
        String purchaseGuid = intent.getStringExtra("PurchaseGuid");

        Realm realm=Realm.getDefaultInstance();
        PurchaseItemModel model= realm.where(PurchaseItemModel.class).equalTo("productGuid", itemGuid).findFirst();
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put("purchaseGuid", purchaseGuid);
        parameters.put("token", GlobalVar.API_TOKEN);

        RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);
        Call<Boolean> call = rest.purchaseItemsEdit(model, parameters);
        try {
            Response resp=call.execute();
            Boolean response = (Boolean) resp.body();
            Log.v("my_log", "edit item " + response.toString());


        } catch (IOException e) {
            e.printStackTrace();

        }
realm.close();
    }
}
