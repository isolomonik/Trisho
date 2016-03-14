package com.isolomonik.trisho.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.isolomonik.trisho.Loaders.PurchaseNamesLoader;
import com.isolomonik.trisho.Loaders.PurchaseNewLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.activities.PurchaseItemsActivity;
import com.isolomonik.trisho.utils.AdapterCallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class DialogNewPurchaseFragment extends DialogFragment implements
        Callback,
        LoaderManager.LoaderCallbacks<String[]> {

    AdapterCallBackInterface myInterface;

    private ListView listView;
    private AutoCompleteTextView inputSearch;
    private String guid;
    private String name;
    private Context cont;
    private ArrayAdapter<String> adapter;
    private Bundle bundle = new Bundle();
    private AsyncTaskLoader<String[]> loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().setTitle("Insert name of purchase");
        View v = inflater.inflate(R.layout.fragment_dialog_new_purchase, container, false);
        listView = (ListView) v.findViewById(R.id.lvPurchaseNames);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                                                inputSearch.setText(((TextView) item).getText());
                                            }
                                        }
        );

        Button btnOK = (Button) v.findViewById(R.id.btnOKNewPurchase);
        btnOK.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Log.d(GlobalVar.MY_LOG, "Dialog Purchase OK pressed");
                                         name = inputSearch.getText().toString();
                                         bundle.putString("name", name);
                                         //      getLoaderManager().restartLoader(GlobalVar.LOADER_PURCHASE_NEW_ID, bundle, DialogNewPurchaseFragment.this);
                                         okNewPurchase();
                                         dismiss();
                                     }
                                 }

        );

        Button btnCancel = (Button) v.findViewById(R.id.btnCancelNewPurchase);
        btnCancel.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Log.d(GlobalVar.MY_LOG, "Dialog Purchase CANCEL pressed");
                                             dismiss();
                                         }
                                     }

        );

        inputSearch = (AutoCompleteTextView) v.findViewById(R.id.etNewPurchaseName);
        inputSearch.addTextChangedListener(new TextWatcher() {
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

        switch (id) {
            case GlobalVar.LOADER_PURCHASE_NAMES_ID: {
                loader = new PurchaseNamesLoader(getActivity(), args);
                break;
            }
            case GlobalVar.LOADER_PURCHASE_NEW_ID: {
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


    private void okNewPurchase() {
        //  cont=this.getContext();
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType type = MediaType.parse("application/json; charset=utf-8");
            JSONObject json = new JSONObject();
            json.put("name", name);
            RequestBody body = RequestBody.create(type, json.toString());
            Request request = new Request.Builder()
                    .url(GlobalVar.URL_API + "api/Purchase?token=" + GlobalVar.API_TOKEN)
                    .put(body)
                    .build();

            client.newCall(request).enqueue(DialogNewPurchaseFragment.this);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String resp = response.body().string();
        guid = resp.replaceAll("\"", "");
        //  AdapterCallBackInterface myInterface = (AdapterCallBackInterface) cont;
        myInterface.showItems(guid, name);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            myInterface = (AdapterCallBackInterface) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
