package com.isolomonik.trisho.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.isolomonik.trisho.R;

public class PurchaseItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_items);

        Intent intent = getIntent();
        String purchaseGuid = intent.getStringExtra("guid");



    }

}
