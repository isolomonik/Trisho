package com.isolomonik.trisho.activities;

//import android.app.FragmentManager;
//import android.app.FragmentTransaction;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.fragments.PurchaseListFragment;
import com.isolomonik.trisho.utils.AdapterCallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

public class PurchaseListActivity extends AppCompatActivity
//implements AdapterCallBackInterface
{
    PurchaseListFragment  purchaseListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  toolbar.setLogo(R.drawable.ic_10d);

        getSupportActionBar().setTitle(R.string.app_name);

        purchaseListFragment=new PurchaseListFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.purchasefragmentcont, purchaseListFragment)
                    .commit();
        }

    }

   // @Override
    public void showItems(String guid) {
        Intent intent = new Intent(this, PurchaseItemsActivity.class);

      //  intent.putExtra("guid", purchaseList.get(position).getGuid());
        intent.putExtra("guid", guid);
        startActivity(intent);
    }
}
