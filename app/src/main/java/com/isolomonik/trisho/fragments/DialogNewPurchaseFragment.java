package com.isolomonik.trisho.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.isolomonik.trisho.Loaders.PurchaseNamesLoader;
import com.isolomonik.trisho.Loaders.PurchaseNewLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.activities.PurchaseItemsActivity;
import com.isolomonik.trisho.utils.GlobalVar;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class DialogNewPurchaseFragment extends DialogFragment implements
        LoaderManager.LoaderCallbacks<String[]> {

    private ListView listView;
    private EditText inputSearch;
    private String guid;
    private String name;
    ArrayAdapter<String> adapter;
    Bundle bundle = new Bundle();
    AsyncTaskLoader<String[]> loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().setTitle("Insert name of purchase");
        View v = inflater.inflate(R.layout.fragment_dialog_new_purchase, container, false);
        listView = (ListView) v.findViewById(R.id.lvPurchaseNames);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
        public void onItemClick(AdapterView<?> parent, View item, int position, long id)
           {
          inputSearch.setText(((TextView) item).getText());
           }
          }
        );

        Button btnOK = (Button) v.findViewById(R.id.btnOKNewPurchase);
        btnOK.setOnClickListener(new View.OnClickListener()
          {
           @Override
           public void onClick(View v) {
            Log.d(GlobalVar.MY_LOG, "Dialog Purchase OK pressed");
               bundle.putString("name", inputSearch.getText().toString());
               getLoaderManager().restartLoader(GlobalVar.LOADER_PURCHASE_NEW_ID, bundle, DialogNewPurchaseFragment.this);

//               OkHttpClient client = new OkHttpClient();
//               RequestBody formBody = new FormEncodingBuilder()
//                       .add("search", "Jurassic Park")
//                       .build();
//               Request request = new Request.Builder()
//                       .url("https://en.wikipedia.org/w/index.php")
//                       .post(formBody)
//                       .build();
//
//               Response response = client.newCall(request).execute();
//
//
//               Intent intent = new Intent(getContext(), PurchaseItemsActivity.class);
//            intent.putExtra("guid", guid);
//            intent.putExtra("purchaseName", name);
//            startActivity(intent);
            dismiss();
            }
           }

        );

        Button btnCancel = (Button) v.findViewById(R.id.btnCancelNewPurchase);
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
         @Override
         public void onClick(View v) {
         Log.d(GlobalVar.MY_LOG, "Dialog Purchase CANCEL pressed");
         dismiss();
          }
         }

        );

        inputSearch = (EditText) v.findViewById(R.id.etNewPurchaseName);
        inputSearch.addTextChangedListener(new TextWatcher()
         {
         @Override
         public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

        //  adapter.getFilter().filter(cs);
         bundle.putString("name", cs.toString());
         getLoaderManager().restartLoader(GlobalVar.LOADER_PURCHASE_NAMES_ID, bundle, DialogNewPurchaseFragment.this);

        }

       @Override
       public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {


     }

     @Override
     public void afterTextChanged(Editable arg0) {


    }
  }

        );
        return v;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(GlobalVar.MY_LOG, "Dialog Purchase: onDismiss");
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(GlobalVar.LOADER_PURCHASE_NAMES_ID, null, this);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(GlobalVar.MY_LOG, "Dialog Purchase: onCancel");
    }

    @Override
    public AsyncTaskLoader<String[]> onCreateLoader(int id, Bundle args) {

        switch (id){
            case GlobalVar.LOADER_PURCHASE_NAMES_ID :{
                  loader = new PurchaseNamesLoader(getActivity(), args);
                  break;
            }
            case GlobalVar.LOADER_PURCHASE_NEW_ID : {
                loader = new PurchaseNewLoader(getActivity(), args);
                break;
            }
        }
       return loader;
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        Log.v(GlobalVar.MY_LOG, "Purchase list load finished");

        if (data == null) {
            Log.v(GlobalVar.MY_LOG, "PurchaseNames is null");
        } else {
            adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, data);

            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {

    }
}
