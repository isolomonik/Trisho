package com.isolomonik.trisho.fragments;


import android.content.Context;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.fasterxml.jackson.databindG.ObjectMapper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isolomonik.trisho.Loaders.RecommendedProductsLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.adapters.CustomProductsAdapter;
import com.isolomonik.trisho.adapters.RecommendedProductsAdapter;
import com.isolomonik.trisho.models.CustomProducts;
import com.isolomonik.trisho.models.NewPurchaseItemsModel;
import com.isolomonik.trisho.models.NewRecommendedItemModel;
import com.isolomonik.trisho.models.RecomendedProductModel;
import com.isolomonik.trisho.utils.AdapterCallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Filter;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewItemsFragment extends Fragment
        implements
        AdapterCallBackInterface,
        LoaderManager.LoaderCallbacks<List<RecomendedProductModel>>
        , Callback<String[]>, okhttp3.Callback {
    private String guid;
    private String purchaseName;
    private ImageButton ibSearchOK;
    private Button btnSave;
  private ProgressBar pbRecom;

    private AutoCompleteTextView inputSearch;
    private String[] products = {""};
    private ArrayAdapter<String> adapterSearch;

    private NewPurchaseItemsModel model;
    private List<CustomProducts> customProductsList = new LinkedList<>();
    private List<RecomendedProductModel> list = new LinkedList<>();
   // private RealmList<RecomendedProductModel> list= new RealmList<>();

    private Realm realm;

    private RecyclerView recyclerView;
    private RecyclerView customRecyclerView;
    private RecommendedProductsAdapter adapter;

    public NewItemsFragment() {
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_items, container, false);
        if (getArguments() != null) {
            guid = getArguments().getString("guid");
            purchaseName = getArguments().getString("purchaseName");
        }

        model = new NewPurchaseItemsModel();
        model.setPurchaseGuid(guid);

        pbRecom = (ProgressBar) v.findViewById(R.id.pbRec);
        btnSave = (Button) v.findViewById(R.id.btnOKItems);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(GlobalVar.MY_LOG, "New items save pressed");
                saveItems();
    //            getActivity().finish();

            }

        });
    //    btnSave.requestFocus();

        ibSearchOK = (ImageButton) v.findViewById(R.id.ibSearchOK);
        ibSearchOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(GlobalVar.MY_LOG, "New product save pressed");
                addFromSearch(inputSearch);
                inputSearch.clearFocus();
                recyclerView.requestFocus();
                final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);


            }

        });

        inputSearch = (AutoCompleteTextView) v.findViewById(R.id.tvInsertNewProduct);
        // EditText inputSearch = (EditText) v.findViewById(R.id.tvInsertNewProduct);
        inputSearch.setThreshold(1);
        inputSearch.addTextChangedListener(new TextWatcher() {
                                               @Override
                                               public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                                 //  btnSave.setVisibility(View.INVISIBLE);
                                                   Log.v(GlobalVar.MY_LOG, "insert new product");

                                                  // adapter.getFilter().filter(cs);
                                                   //getLoaderManager().restartLoader(GlobalVar.LOADER_PURCHASE_NAMES_ID, bundle, DialogNewPurchaseFragment.this);
                                                   if (cs != null) {
                                                   //    ibSearchOK.setImageResource(@android:drawable/ic_menu_search);

                                                       RetrofitAPIInterface rest = APIFactory.getAPI(GlobalVar.URL_API);
                                                       Map<String, String> parameters = new HashMap<String, String>();
                                                       parameters.put("name", cs.toString());
                                                       parameters.put("take", "200");
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
                                                   Log.v(GlobalVar.MY_LOG, "afterTextChanged "+arg0.toString());
                                               }
                                           }

        );
        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    addFromSearch(v);

                }
             //   btnSave.setVisibility(View.VISIBLE);
                return false;
            }
        });
        adapterSearch = new ArrayAdapter<String>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, products);
        inputSearch.setAdapter(adapterSearch);

        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputSearch.clearFocus();
       // btnSave.requestFocus();
        pbRecom.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) view.findViewById(R.id.lvRecomendedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
recyclerView.requestFocus();

//        customRecyclerView = (RecyclerView) view.findViewById(R.id.lvNewProducts);
//        customRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        customRecyclerView.setAdapter(new CustomProductsAdapter(this, customProductsList));
//        customRecyclerView.setVisibility(View.INVISIBLE);

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
            result.sort("isFeaturedProducts");
            // RealmList<RecomendedProductModel> list = new RealmList<>();
            list.addAll(result);
            realm.commitTransaction();
            //  ArrayList
            adapter = new RecommendedProductsAdapter(this, list);
            recyclerView.setAdapter(adapter);
        }
 // pbRecom.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<RecomendedProductModel>> loader) {

    }

    // -------- for Retrofit
    @Override
    public void onResponse(Call<String[]> call, Response<String[]> response) {
        products = response.body();
        if (products != null) {
            adapterSearch.notifyDataSetChanged();
            adapterSearch = new ArrayAdapter<String>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, products);

            inputSearch.setAdapter(adapterSearch);
        }
    }

    @Override
    public void onFailure(Call<String[]> call, Throwable t) {

    }

// --end for Retrofit

    @Override
    public void onResume() {
        super.onResume();
        btnSave.requestFocus();

    }

    //----------- for Realm
    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
        btnSave.requestFocus();

    }

    @Override
    public  void onPause(){
       // saveItems();
        super.onPause();
    }

    @Override
    public void onStop() {

        realm.close();
        super.onStop();
    }
    //--end for Realm

    //-------for adapter
    @Override
    public void showItems(String str1, String str2) {
        NewRecommendedItemModel addRecom = new NewRecommendedItemModel();
        addRecom.setProductGuid(str1);
        addRecom.setCount(Double.parseDouble(str2));
        model.getRecomendedProductses().add(addRecom);
    }

    //-------end for adapter



    private void addFromSearch (TextView v){
        String newName = v.getText().toString();
        if (!newName.equals("")) {
            CustomProducts customProducts = new CustomProducts(newName, 0);
            RecomendedProductModel customProd = new RecomendedProductModel();
            customProd.setProductName(newName);
            customProd.setStatus(1);
            model.getCustomProductses().add(customProducts);
            customProductsList.add(customProducts);
            realm.beginTransaction();
            list.add(0, customProd);
            realm.commitTransaction();

            //        customRecyclerView.setAdapter(new CustomProductsAdapter(NewItemsFragment.this, customProductsList));
            v.setText("");
            //        list.add(0,customProd);
            //        customRecyclerView.setVisibility(View.VISIBLE);

            //adapter = new RecommendedProductsAdapter(NewItemsFragment.this, list);
            recyclerView.setAdapter(new RecommendedProductsAdapter(NewItemsFragment.this, list));
        }
    }



    private void saveItems() {
        Log.v(GlobalVar.MY_LOG, "save items started");

        try {
            Gson gson = new GsonBuilder()
          //          .setPrettyPrinting()
                    .create();
            String json = gson.toJson(model);
            OkHttpClient client = new OkHttpClient();
            MediaType type = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(type, json);
            Request request = new Request.Builder()
                    .url(GlobalVar.URL_API + "api/PurchaseItem?token=" + GlobalVar.API_TOKEN)
                    .put(body)
                    .build();

            client.newCall(request).enqueue(this);
            Log.v(GlobalVar.MY_LOG,json );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(okhttp3.Call call, IOException e) {
        Log.v(GlobalVar.MY_LOG,"items don't added "+ e );
    }

    @Override
    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
        Log.v(GlobalVar.MY_LOG,"items added to purchase " );
        getActivity().finish();
    }
}