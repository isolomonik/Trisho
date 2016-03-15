package com.isolomonik.trisho.fragments;


import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.isolomonik.trisho.Loaders.PurchaseItemsLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.activities.NewItemActivity;
import com.isolomonik.trisho.adapters.PurchaseItemsAdapter;
import com.isolomonik.trisho.models.PurchaseItemModel;
import com.isolomonik.trisho.utils.GlobalVar;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class ItemsListFragment extends Fragment implements
     //   AdapterCallBackInterface,
        LoaderManager.LoaderCallbacks<List<PurchaseItemModel>>
{
    Realm realm;
    String guid;
    String purchaseName;
    private RecyclerView recyclerView;
    private PurchaseItemsAdapter adapter;

    public ItemsListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View v =inflater.inflate(R.layout.fragment_items, container, false);
        if(getArguments() != null){
            guid = getArguments().getString("guid");
            purchaseName = getArguments().getString("purchaseName");}
        ((TextView) v.findViewById(R.id.tvPurchaseName)).setText(purchaseName);
        Button btnAdd= (Button) v.findViewById(R.id.btnAddItem);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemsListFragment.this.getContext(), NewItemActivity.class);
                intent.putExtra("guid", guid);
             //   intent.putExtra("purchaseName", name);
                startActivity(intent);
            }
        });

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
        Bundle bundle = new Bundle();
        bundle.putString("guid", guid);
        Log.v(GlobalVar.MY_LOG, bundle.toString());
        getLoaderManager().initLoader(GlobalVar.LOADER_PURCHASE_ITEMS_ID, bundle, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = (RecyclerView) view.findViewById(R.id.lvItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        realm = Realm.getInstance(getContext());


    }

    @Override
    public Loader<List<PurchaseItemModel>> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<List<PurchaseItemModel>> itemsLoader = new PurchaseItemsLoader(getActivity(), args);
        return itemsLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<PurchaseItemModel>> loader, List<PurchaseItemModel> data) {
        Log.v(GlobalVar.MY_LOG, "Purchase items load finished");


        if ((data == null) || (realm == null)) {
            Log.v(GlobalVar.MY_LOG, "PurchaseItems is null");
            return;
        }
        realm.beginTransaction();
        realm.clear(PurchaseItemModel.class);  // Clear the DB
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
        Log.v(GlobalVar.MY_LOG, "to realm inserted" + realm.allObjects(PurchaseItemModel.class).size());
        realm.beginTransaction();
        RealmResults<PurchaseItemModel> result = realm.where(PurchaseItemModel.class).findAll();
        realm.commitTransaction();
        adapter = new PurchaseItemsAdapter(this, result);
        recyclerView.setAdapter(adapter);
        //   purchaseList.addAll(result);
    }

    @Override
    public void onLoaderReset(Loader<List<PurchaseItemModel>> loader) {

    }

    @Override
    public void onStart() {
        super.onStart();
        // realm = Realm.getDefaultInstance();
        try {
            realm = Realm.getInstance(this.getContext());
        }catch (Exception e){e.printStackTrace();}

    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}