package com.isolomonik.trisho.fragments;

//import android.content.AsyncTaskLoader;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.isolomonik.trisho.Loaders.RegisterLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.Loaders.LoginLoader;
import com.isolomonik.trisho.models.LoginModel;
import com.isolomonik.trisho.utils.FragmentCallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import io.realm.Realm;

//import retrofit2.Response;


public class LoginFragment extends Fragment implements LoaderManager.LoaderCallbacks<LoginModel>
//, Callback<String>

{

    private EditText telephone;
    private EditText password;
    private FragmentCallBackInterface fragmentCallBackInterface;


    private AsyncTaskLoader<LoginModel> loginLoader;
    private AsyncTaskLoader<String> registerLoader;

    private Realm realm;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCallBackInterface = (FragmentCallBackInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        telephone = (EditText) v.findViewById(R.id.telephoneEdit);
        password = (EditText) v.findViewById(R.id.passwordEdit);

        telephone.setText(GlobalVar.API_TELEPHONE);
        password.setText(GlobalVar.API_PASSWORD);
        v.findViewById(R.id.singinBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    testHTTP();
            //    testOKHttp();

            startLoginLoader();
            }
        });

        v.findViewById(R.id.btnNewUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCallBackInterface.newUserSubmit();
            }
        });
        return v;

    }

    private void startLoginLoader() {
        Bundle bundle = new Bundle();
        bundle.putString("Telephone", telephone.getText().toString());
        bundle.putString("Password", password.getText().toString());
        Log.v(GlobalVar.MY_LOG, bundle.toString());
        getLoaderManager().restartLoader(GlobalVar.LOADER_LOGIN_ID, bundle, LoginFragment.this);
      //   getLoaderManager().restartLoader(GlobalVar.LOADER_REGISTER_ID, bundle, LoginFragment.this);
    }


    //----to implement Loaders

    public AsyncTaskLoader<LoginModel> onCreateLoader(int id, Bundle args) {

return new LoginLoader(getActivity(), args);
    }

    public void onLoadFinished(Loader<LoginModel> loader, LoginModel data) {
        if (data.getToken()!=null) {
            Log.i(GlobalVar.MY_LOG, data.getToken());
            GlobalVar.API_TOKEN = data.getToken();

            LoginModel loginModel = new LoginModel();
            loginModel.setTelephone(telephone.getText().toString());
            loginModel.setPassword(password.getText().toString());
            loginModel.setUserName(data.getUserName());
            loginModel.setToken(data.getToken());
            loginModel.setUserGuid(data.getUserGuid());


            realm.beginTransaction();
            realm.clear(LoginModel.class);
            realm.copyToRealmOrUpdate(loginModel);
            realm.commitTransaction();
            Log.v(GlobalVar.MY_LOG, "loginModel saved in realm");

            getActivity().finish();
            fragmentCallBackInterface.loginSubmit();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Не удалось! Возможно, имя, пароль указаны не верно")
                    .setCancelable(false)
                    //.setView("Не удалось подключиться. Возможно, имя, пароль указаны не верно")
                    .setPositiveButton("Ок",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
                    }
    }

    public void onLoaderReset(Loader<LoginModel> loader) {

        Log.i(GlobalVar.MY_LOG, "onLoaderReset");
     //   GlobalVar.API_TOKEN="";
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();

    }

    @Override
    public void onStop() {
         realm.close();
        super.onStop();
    }


}
