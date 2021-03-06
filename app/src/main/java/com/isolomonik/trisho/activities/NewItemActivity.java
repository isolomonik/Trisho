package com.isolomonik.trisho.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.fragments.NewItemsFragment;

public class NewItemActivity extends AppCompatActivity {
    private String purchaseGuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        Intent intent = getIntent();
        purchaseGuid = intent.getStringExtra("guid");

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        //  toolbar.setLogo(R.drawable.ic_10d);

        // getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NewItemsFragment newItemsFragment=new NewItemsFragment();
        if (savedInstanceState == null) {

            newItemsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.newitemsfragmentcont, newItemsFragment)
                    .commit();
        }



    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }
}
