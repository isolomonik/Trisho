package com.isolomonik.trisho.fragments;


import android.content.Context;
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
import android.widget.EditText;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isolomonik.trisho.Loaders.RegisterLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.RestAPI.APIFactory;
import com.isolomonik.trisho.RestAPI.RetrofitAPIInterface;
import com.isolomonik.trisho.models.LoginModel;
import com.isolomonik.trisho.models.RegisterModel;
import com.isolomonik.trisho.utils.FragmentCallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;



public class NewUserFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>

      // , Callback<String>
{

    FragmentCallBackInterface fragmentCallBackInterface;

private EditText name;
private EditText email;
private EditText telephone;
    private EditText password;
private  Realm realm;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        fragmentCallBackInterface = (FragmentCallBackInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_new_user, container, false);
        name=(EditText) v.findViewById(R.id.editTextName);
        email=(EditText) v.findViewById(R.id.editTextEmail);
        telephone=(EditText) v.findViewById(R.id.editTextTelephoneNewUser);
        password=(EditText) v.findViewById(R.id.editTextPassw);

        v.findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Telephone", telephone.getText().toString());
                bundle.putString("Password", password.getText().toString());
                bundle.putString("Name", name.getText().toString());
                bundle.putString("Email", email.getText().toString());
                Log.v(GlobalVar.MY_LOG, bundle.toString());
                getLoaderManager().restartLoader(GlobalVar.LOADER_REGISTER_ID, bundle, NewUserFragment.this);
            }
        });
        return v;
    }



    @Override
    public AsyncTaskLoader<String> onCreateLoader(int id, Bundle args) {

        return  new RegisterLoader(getActivity(), args);

    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        Log.d(GlobalVar.MY_LOG, data);
        GlobalVar.API_TOKEN = data;

        RegisterModel  model = new RegisterModel();
        model.setTelephone(telephone.getText().toString());
        model.setPassword(password.getText().toString());
        model.setName(name.getText().toString());
        model.setEmail(email.getText().toString());
        model.setToken(data);


        realm.beginTransaction();
        LoginModel loginModel=new LoginModel();
        loginModel.setTelephone(model.getTelephone());
        loginModel.setPassword(model.getPassword());
        loginModel.setToken(data);
        realm.clear(LoginModel.class);
     //   realm.clear(RegisterModel.class);
        realm.copyToRealmOrUpdate(loginModel);
      //  realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
        Log.v(GlobalVar.MY_LOG, "Register saved in realm");


        fragmentCallBackInterface.loginSubmit();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

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
