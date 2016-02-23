package com.isolomonik.trisho.activities;

//import android.app.FragmentManager;
//import android.app.FragmentTransaction;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.fragments.PurchaseListFragment;
import com.isolomonik.trisho.utils.GlobalVar;

public class PurchaseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  toolbar.setLogo(R.drawable.ic_10d);

        getSupportActionBar().setTitle(R.string.app_name);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.purchasefragmentcont, new PurchaseListFragment())
                    .commit();
        }

    }
}
