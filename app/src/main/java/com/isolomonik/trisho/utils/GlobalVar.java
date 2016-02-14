package com.isolomonik.trisho.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class GlobalVar {


    public static final String MY_LOG = "Trisho";

    public static boolean IS_NetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

public static String API_TOKEN="";
public static String API_TELEPHONE="";
public static String API_PASSWORD="";
public static String URL_API="http://solomon-001-site1.btempurl.com";
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_TOKEN = "";



    public static int STATUS_ADD=1;
    public static int STATUS_DONE=2;
    public static int STATUS_IGNOR=3;

}

