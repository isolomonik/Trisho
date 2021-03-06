package com.isolomonik.trisho.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.isolomonik.trisho.activities.MainActivity;
import com.isolomonik.trisho.models.LoginModel;

import io.realm.Realm;

public class GlobalVar {


    public static final String MY_LOG = "Trisho";

    public static boolean IS_NetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    public static void Logout (Context context){
        API_TOKEN="";
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.clear(LoginModel.class);
        realm.commitTransaction();
        Log.v(GlobalVar.MY_LOG, "loginModel saved in realm");
        realm.close();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        //finish();


    };

public static String API_TOKEN="";
public static String API_TELEPHONE="";
public static String API_PASSWORD="";
public static String API_USERNAME="";

public static String URL_API="http://solomon-001-site1.btempurl.com/";

    public static final int LOADER_LOGIN_ID = 1;
    public static final int LOADER_REGISTER_ID = 2;
    public static final int LOADER_PURCHASE_LIST_ID = 3;
    public static final int LOADER_PURCHASE_ITEMS_ID = 4;
    public static final int LOADER_PURCHASE_NAMES_ID = 5;
    public static final int LOADER_PURCHASE_NEW_ID = 6;
    public static final int LOADER_RECOMMENDED_PRODUCTS_ID = 7;
    public static final int LOADER_PRODUCTS_ID = 8;
    public static final int LOADER_PURCHASE_EDIT = 9;



    public static String STATUS_ADD="Added";
    public static String STATUS_DONE="Done";
    public static String STATUS_IGNOR="Ignored";

    public static final int STATUS_NEW=1;

}

