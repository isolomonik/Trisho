package com.isolomonik.trisho.fragments;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.isolomonik.trisho.Loaders.PurchaseListLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.activities.PurchaseItemsActivity;
import com.isolomonik.trisho.adapters.PurchaseListAdapter;
import com.isolomonik.trisho.models.PurchaseModel;
import com.isolomonik.trisho.recycler_helper.SimpleItemTouchHelperCallback;
import com.isolomonik.trisho.utils.AdapterCallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;


public class PurchaseListFragment extends Fragment implements
        AdapterCallBackInterface,
        LoaderManager.LoaderCallbacks<List<PurchaseModel>> {

    private Realm realm;
    private RealmList<PurchaseModel> purchaseList = new RealmList<>();
    private ArrayList<PurchaseModel> changedList=new ArrayList<>();

    private RecyclerView recyclerView;
    private PurchaseListAdapter adapter;
    private ItemTouchHelper itemTouchHelper;

    private Button btnAddPurchase;
    private  SwipeRefreshLayout sLay;
    ProgressBar pbList;

    public PurchaseListFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(GlobalVar.LOADER_PURCHASE_LIST_ID, null, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

   //     getLoaderManager().initLoader(GlobalVar.LOADER_PURCHASE_LIST_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_purchase_list, container, false);

        pbList =(ProgressBar) v.findViewById(R.id.pbListPurch);
        btnAddPurchase =(Button) v.findViewById(R.id.btnAddPurchase);
        btnAddPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dlgNewPurchase=new DialogNewPurchaseFragment();
                dlgNewPurchase.show(getFragmentManager(), "insert new purchase");
            }
        });
        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sLay=(SwipeRefreshLayout)view.findViewById(R.id.swipePurchase);
//

        recyclerView = (RecyclerView) view.findViewById(R.id.listView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        sLay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sLay.setRefreshing(true);
                getLoaderManager().restartLoader(GlobalVar.LOADER_PURCHASE_LIST_ID, null, PurchaseListFragment.this);
                // Refresh items
                Log.v(GlobalVar.MY_LOG, "PurchaseList is updated");
            }
        });
        recyclerView.setLayoutManager(layoutManager);


     //  initAdapter();

    }

    @Override
    public AsyncTaskLoader<List<PurchaseModel>> onCreateLoader(int id, Bundle args) {
        sLay.setRefreshing(true);
        AsyncTaskLoader<List<PurchaseModel>> listLoader = new PurchaseListLoader(getActivity(), args);
        return listLoader;
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

        realm.beginTransaction();
      //  RealmList<PurchaseModel> result = realm.where(PurchaseModel.class).findAll();
        RealmResults<PurchaseModel> result = realm.where(PurchaseModel.class).findAll();
        realm.commitTransaction();

        purchaseList.clear();
        purchaseList.addAll(result);
       // adapter = new PurchaseListAdapter(this, result);
        adapter = new PurchaseListAdapter(this, purchaseList);
        recyclerView.setAdapter(adapter);
        sLay.setRefreshing(false);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        pbList.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<List<PurchaseModel>> loader) {

    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
//
   }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onStop() {

        realm.close();
        super.onStop();

    }

    @Override
    public void showItems(String guid, String name) {
       // String guid= purchaseList.get(position).getGuid();
        Intent intent = new Intent(getContext(), PurchaseItemsActivity.class);
        intent.putExtra("guid", guid);
        intent.putExtra("purchaseName", name);
        startActivity(intent);
    }


}
