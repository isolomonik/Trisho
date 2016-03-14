package com.isolomonik.trisho.fragments;


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

import com.isolomonik.trisho.Loaders.RecommendedProductsLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.adapters.RecommendedProductsAdapter;
import com.isolomonik.trisho.models.RecomendedProductModel;
import com.isolomonik.trisho.utils.GlobalVar;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class NewItemsFragment extends Fragment
        implements
        //   AdapterCallBackInterface,
        LoaderManager.LoaderCallbacks<List<RecomendedProductModel>>
{
    private String guid;
    private String purchaseName;

    Realm realm;

    private RecyclerView recyclerView;
    private RecommendedProductsAdapter adapter;

    public NewItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View v=   inflater.inflate(R.layout.fragment_new_items, container, false);
        if(getArguments() != null){
            guid = getArguments().getString("guid");
            purchaseName = getArguments().getString("purchaseName");}

        Button btnCancel = (Button) v.findViewById(R.id.btnCancelSaveItems);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(GlobalVar.MY_LOG, "Dialog Purchase CANCEL pressed");
                getActivity().onBackPressed();

            }

        });
      return  v;
}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.lvRecomendedItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        realm = Realm.getInstance(getContext());


    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
        Bundle bundle = new Bundle();
        bundle.putString("guid", guid);
        Log.v(GlobalVar.MY_LOG, bundle.toString());
        getLoaderManager().initLoader(GlobalVar.LOADER_RECOMMENDED_PRODUCTS_ID, bundle, this);
    }

    @Override
    public Loader<List<RecomendedProductModel>> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<List<RecomendedProductModel>> itemsLoader = new RecommendedProductsLoader(getActivity(), args);
        return itemsLoader;

    }

    @Override
    public void onLoadFinished(Loader<List<RecomendedProductModel>> loader, List<RecomendedProductModel> data) {
        Log.v(GlobalVar.MY_LOG, "Purchase items load finished");


        if ((data == null) || (realm == null)) {
            Log.v(GlobalVar.MY_LOG, "PurchaseItems is null");
            return;
        }else {
            realm.beginTransaction();
            realm.clear(RecomendedProductModel.class);  // Clear the DB
            realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();
            Log.v(GlobalVar.MY_LOG, "to realm inserted" + realm.allObjects(RecomendedProductModel.class).size());

            realm.beginTransaction();
            RealmResults<RecomendedProductModel> result = realm.where(RecomendedProductModel.class).findAll();
            realm.commitTransaction();
            adapter = new RecommendedProductsAdapter(this, result);
            recyclerView.setAdapter(adapter);
           }

        }

    @Override
    public void onLoaderReset(Loader<List<RecomendedProductModel>> loader) {

    }
}