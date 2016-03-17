package com.isolomonik.trisho.fragments;


import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.isolomonik.trisho.Loaders.RecommendedProductsLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.adapters.RecommendedProductsAdapter;
import com.isolomonik.trisho.models.RecomendedProductModel;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Filter;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewItemsFragment extends Fragment
        implements
        //   AdapterCallBackInterface,
        LoaderManager.LoaderCallbacks<List<RecomendedProductModel>>
        , Callback<String[]> {
    private String guid;
    private String purchaseName;

    AutoCompleteTextView inputSearch;
    String[] products={""};
    ArrayAdapter<String> adapterSearch;

    private Realm realm;

    private RecyclerView recyclerView;
    private RecommendedProductsAdapter adapter;

    public NewItemsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_items, container, false);
        if (getArguments() != null) {
            guid = getArguments().getString("guid");
            purchaseName = getArguments().getString("purchaseName");
        }

        Button btnCancel = (Button) v.findViewById(R.id.btnCancelSaveItems);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(GlobalVar.MY_LOG, "Dialog Purchase CANCEL pressed");
                getActivity().onBackPressed();

            }

        });


        // Получаем ссылку на элемент AutoCompleteTextView в разметке
         inputSearch = (AutoCompleteTextView) v.findViewById(R.id.tvInsertNewProduct);
        // EditText inputSearch = (EditText) v.findViewById(R.id.tvInsertNewProduct);
        inputSearch.setThreshold(1);
        inputSearch.addTextChangedListener(new TextWatcher() {
                                               @Override
                                               public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                                   Log.v(GlobalVar.MY_LOG, "insert new product");
                                                   // adapter.getFilter().filter(cs);
                                                   //getLoaderManager().restartLoader(GlobalVar.LOADER_PURCHASE_NAMES_ID, bundle, DialogNewPurchaseFragment.this);
                                                  if (cs!=null) {
                                                      RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);
                                                      Map<String, String> parameters = new HashMap<String, String>();
                                                      parameters.put("name", cs.toString());
                                                      parameters.put("take", "50");
                                                      parameters.put("skip", "0");
                                                      parameters.put("token", GlobalVar.API_TOKEN);
                                                      Call<String[]> call = rest.productsNames(parameters);
                                                      try {
                                                          call.enqueue(NewItemsFragment.this);
                                                          if (products != null) {
                                                              Log.v("my_log", "loaded poductsNames size:" + products.length);

                                                              //onSuccess();
                                                          } else {
                                                              // onError();
                                                          }

                                                      } catch (Exception e) {
                                                          e.printStackTrace();

                                                      }
                                                  }
                                               }

                                               @Override
                                               public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


                                               }

                                               @Override
                                               public void afterTextChanged(Editable arg0) {


                                               }
                                           }

        );
        // Получаем массив строк для автозаполнения
      // products = {"Pizza", "Peper", "Murshmulo", "kjghfgfdfj", "kakao", "coffe"};
        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        adapterSearch = new ArrayAdapter<String>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, products);
        inputSearch.setAdapter(adapterSearch);

        Button btnAdd = (Button) v.findViewById(R.id.btnSaveNewProduct);
        btnAdd.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                     //    name = inputSearch.getText().toString();

                                     }
                                 }

        );
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.lvRecomendedItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);



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
        } else {
            realm.beginTransaction();
            realm.clear(RecomendedProductModel.class);  // Clear the DB
            realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();
            Log.v(GlobalVar.MY_LOG, "to realm inserted" + realm.allObjects(RecomendedProductModel.class).size());

            realm.beginTransaction();
            RealmResults<RecomendedProductModel> result = realm.where(RecomendedProductModel.class).findAll();
            realm.commitTransaction();
            //  ArrayList
            adapter = new RecommendedProductsAdapter(this, result);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<RecomendedProductModel>> loader) {

    }
// -------- for Retrofit
    @Override
    public void onResponse(Call<String[]> call, Response<String[]> response) {
        products=response.body();
        if (products!=null) {
            adapterSearch.notifyDataSetChanged();
            adapterSearch = new ArrayAdapter<String>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, products);

            inputSearch.setAdapter(adapterSearch);
        }
    }

    @Override
    public void onFailure(Call<String[]> call, Throwable t) {

    }

// --end for Retrofit

//----------- for Realm
    @Override
    public void onStart() {
        super.onStart();
        try {
            realm = Realm.getDefaultInstance();
        }catch (Exception e){e.printStackTrace();}

    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    //--end for Realm
}