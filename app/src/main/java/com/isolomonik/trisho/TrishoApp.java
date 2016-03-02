package com.isolomonik.trisho;

import android.app.Application;
import android.content.SharedPreferences;

import com.isolomonik.trisho.utils.GlobalVar;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class TrishoApp extends Application {

    private SharedPreferences mSettings;
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalVar gv=new GlobalVar();
//        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
//        Realm.setDefaultConfiguration(realmConfiguration);


        RealmConfiguration config = new RealmConfiguration.Builder(this)
        //        .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

}
