package com.isolomonik.trisho.activities;


//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.fragments.LoginFragment;
import com.isolomonik.trisho.fragments.NewUserFragment;
import com.isolomonik.trisho.fragments.PurchaseListFragment;
import com.isolomonik.trisho.utils.CallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

public class MainActivity extends AppCompatActivity implements CallBackInterface {
    FragmentManager fm = this.getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    LoginFragment loginFragment;
    PurchaseListFragment purchaseListFragment;
  //  NewUserFragment newUserFragment;

    @Override
    public void newUserSubmit() {
//        fragmentTransaction = fm.beginTransaction();
//        newUserFragment = new NewUserFragment();
//        fragmentTransaction.replace(R.id.maincont, loginFragment);
//        fragmentTransaction.commit();

        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
    }

    @Override
    public void registerSubmit() {

    }

    @Override
   public void loginSubmit() {
        purchaseListFragment = new PurchaseListFragment();
        Log.v(GlobalVar.MY_LOG,GlobalVar.API_TOKEN);
       // fragmentTransaction.detach(loginFragment);
        fm.beginTransaction().replace(R.id.maincont, purchaseListFragment)
                            .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //       GlobalVar gv=new GlobalVar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  toolbar.setLogo(R.drawable.ic_10d);

        getSupportActionBar().setTitle(R.string.app_name);

        if (savedInstanceState == null) {
            if (GlobalVar.IS_NetworkAvailable(this)) {

                initFragments();
            } else {
                Toast.makeText(this, R.string.no_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initFragments() {
        if (GlobalVar.API_TOKEN.equals("") | GlobalVar.API_TOKEN == null) {
            fragmentTransaction = fm.beginTransaction();
            loginFragment = new LoginFragment();
            fragmentTransaction.add(R.id.maincont, loginFragment);
            fragmentTransaction.commit();

        } else {
            PurchaseListFragment purchaseListFragment = new PurchaseListFragment();
            fragmentTransaction.add(R.id.maincont, purchaseListFragment).commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}
