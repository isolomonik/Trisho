package com.isolomonik.trisho.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.AsyncTaskLoader;
//import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.isolomonik.trisho.R;
import com.isolomonik.trisho.RestAPI.LoginLoader;
import com.isolomonik.trisho.utils.CallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;


public class LoginFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<String> {

private     EditText telephone;
  private   EditText password;
private CallBackInterface callBackInterface;

    static final int LOADER_LOGIN_ID = 1;
    private AsyncTaskLoader<String> loginLoader;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        callBackInterface = (CallBackInterface) getActivity();
       // getLoaderManager().initLoader(LOADER_LOGIN_ID, null, this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackInterface = (CallBackInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        telephone=(EditText)v.findViewById(R.id.telephoneEdit);
        password=(EditText)v.findViewById(R.id.passwordEdit);
        v.findViewById(R.id.singinBtn).setOnClickListener(this);
        return v;

    }

    public AsyncTaskLoader<String> onCreateLoader(int id, Bundle args) {

         loginLoader = new LoginLoader(getActivity(), args);
        return loginLoader;
    }

    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d(GlobalVar.MY_LOG, data);
         GlobalVar.API_TOKEN=data;
      //  callBackInterface.loginSubmit();
       //  getActivity().recreate();
//submit();

    }

    public void onLoaderReset(Loader<String> loader) {
        Log.d(GlobalVar.MY_LOG, "onLoaderReset");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.singinBtn:
                Bundle bundle = new Bundle();
                bundle.putString("telephone",telephone.getText().toString());
                GlobalVar.API_TELEPHONE=telephone.getText().toString();
                bundle.putString("password", password.getText().toString());
                GlobalVar.API_PASSWORD=password.getText().toString();
                Log.v(GlobalVar.MY_LOG, bundle.toString());
                getLoaderManager().restartLoader(LOADER_LOGIN_ID, bundle, LoginFragment.this);
//                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
//                progressDialog.setMessage(getResources().getString(R.string.waiting));
//                progressDialog.setIndeterminate(true);
//                progressDialog.show();

 //               callBackInterface.loginSubmit();
                break;
        }
    }


}
