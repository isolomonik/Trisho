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
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.fragments.LoginFragment;
import com.isolomonik.trisho.fragments.PurchaseListFragment;
import com.isolomonik.trisho.models.LoginModel;
import com.isolomonik.trisho.utils.FragmentCallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class MainActivity extends AppCompatActivity implements FragmentCallBackInterface {
    FragmentManager fm = this.getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    LoginFragment loginFragment;
    PurchaseListFragment purchaseListFragment;
  //  NewUserFragment newUserFragment;

   private Realm realm;


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
        Log.v(GlobalVar.MY_LOG, GlobalVar.API_TOKEN);
//fragmentTransaction.
 //       fragmentTransaction.remove(loginFragment);
 //       fragmentTransaction.commit();
        Intent intent = new Intent(this, PurchaseListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);
        //       GlobalVar gv=new GlobalVar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  toolbar.setLogo(R.drawable.ic_10d);

        getSupportActionBar().setTitle(R.string.app_name);

        if (savedInstanceState == null) {
            if (GlobalVar.IS_NetworkAvailable(this)) {


                try {
                    if (realm.allObjects(LoginModel.class).size() != 0) {
                    realm.beginTransaction();
                    RealmResults<LoginModel> result = realm.where(LoginModel.class).findAll();

                    if (result.size()>0) {
                       LoginModel loginModel=result.get(0);
                        GlobalVar.API_TOKEN=loginModel.getToken();
                        GlobalVar.API_TELEPHONE=loginModel.getTelephone();
                        GlobalVar.API_PASSWORD=loginModel.getPassword();
                        GlobalVar.API_USERNAME=loginModel.getUserName();

                    }
                    realm.commitTransaction();

                } else {
                }
                } catch (RealmMigrationNeededException ex) {
                    ex.printStackTrace();
                }

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
            Intent intent = new Intent(this, PurchaseListActivity.class);
            startActivity(intent);
            finish();
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
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}
