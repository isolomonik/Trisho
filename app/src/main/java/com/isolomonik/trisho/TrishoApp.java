package com.isolomonik.trisho;

import android.app.Application;
import android.content.SharedPreferences;

import com.isolomonik.trisho.utils.GlobalVar;


public class TrishoApp extends Application {

    private SharedPreferences mSettings;
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalVar gv=new GlobalVar();
    }

}
