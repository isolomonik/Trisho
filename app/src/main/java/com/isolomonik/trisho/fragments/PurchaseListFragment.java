package com.isolomonik.trisho.fragments;



import android.content.Context;
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
import android.widget.TextView;

import com.isolomonik.trisho.Loaders.PurchaseListLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.activities.PurchaseItemsActivity;
import com.isolomonik.trisho.adapters.PurchaseListAdapter;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.utils.AdapterCallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class PurchaseListFragment extends Fragment implements
        AdapterCallBackInterface,
        LoaderManager.LoaderCallbacks<List<PurchaseModel>> {

    Realm realm;

    private RecyclerView recyclerView;
    private PurchaseListAdapter adapter;
    private ArrayList<PurchaseModel> purchaseList = new ArrayList<>();


    public PurchaseListFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        getLoaderManager().initLoader(GlobalVar.LOADER_PURCHASE_LIST_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_purchase_list, container, false);

        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = (RecyclerView) view.findViewById(R.id.listView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        RealmResults<PurchaseModel> result = realm.where(PurchaseModel.class).findAll();
        realm.commitTransaction();
        adapter = new PurchaseListAdapter(this, result);
        recyclerView.setAdapter(adapter);
     //   purchaseList.addAll(result);

    }

    @Override
    public AsyncTaskLoader<List<PurchaseModel>> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<List<PurchaseModel>> nbuLoader = new PurchaseListLoader(getActivity(), args);
        return nbuLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<PurchaseModel>> loader, List<PurchaseModel> data) {
        Log.v(GlobalVar.MY_LOG, "Purchase list load finished");

        if ((data == null) || (realm == null)) {
            Log.v(GlobalVar.MY_LOG, "PurchaseList is null");
            return;
        }
        realm.beginTransaction();
        realm.clear(PurchaseModel.class);  // Clear the DB
        realm.copyToRealmOrUpdate(data);
        realm.commitTransaction();
        Log.v(GlobalVar.MY_LOG, "to realm inserted" + realm.allObjects(PurchaseModel.class).size());
    }

    @Override
    public void onLoaderReset(Loader<List<PurchaseModel>> loader) {

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

    @Override
    public void showItems(String guid) {
       // String guid= purchaseList.get(position).getGuid();
        Intent intent = new Intent(getContext(), PurchaseItemsActivity.class);
        intent.putExtra("guid",guid);
        startActivity(intent);
    }


}
