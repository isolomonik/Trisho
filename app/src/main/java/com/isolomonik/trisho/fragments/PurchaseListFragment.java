package com.isolomonik.trisho.fragments;



import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isolomonik.trisho.Loaders.PurchaseListLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.utils.GlobalVar;

import junit.framework.Test;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class PurchaseListFragment extends Fragment implements  LoaderManager.LoaderCallbacks<List<PurchaseModel>> {

    public TextView tvTest;
    private Realm realm;

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
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}
