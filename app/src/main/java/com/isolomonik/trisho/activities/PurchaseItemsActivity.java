package com.isolomonik.trisho.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.fragments.ItemsListFragment;

public class PurchaseItemsActivity extends AppCompatActivity {
    private ItemsListFragment productFragment;
    private String purchaseGuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_items);

        Intent intent = getIntent();
        purchaseGuid = intent.getStringExtra("guid");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  toolbar.setLogo(R.drawable.ic_10d);

        getSupportActionBar().setTitle(R.string.labelProducts);
      //  getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productFragment=new ItemsListFragment();
        if (savedInstanceState == null) {

            productFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.itemsfragmentcont, productFragment)
                    .commit();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }



}
