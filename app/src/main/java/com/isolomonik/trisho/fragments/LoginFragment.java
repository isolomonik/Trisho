package com.isolomonik.trisho.fragments;

//import android.content.AsyncTaskLoader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.isolomonik.trisho.Loaders.RegisterLoader;
import com.isolomonik.trisho.R;
import com.isolomonik.trisho.Loaders.LoginLoader;
import com.isolomonik.trisho.RestAPI.RESTRetrofitInterface;
import com.isolomonik.trisho.models.LoginModel;
import com.isolomonik.trisho.utils.CallBackInterface;
import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

public class LoginFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>
//, Callback<String>

{

    private EditText telephone;
    private EditText password;
    private CallBackInterface callBackInterface;


    private AsyncTaskLoader<String> loginLoader;
    private AsyncTaskLoader<String> registerLoader;

    static final int LOADER_REGISTER_ID = 2;
    private AsyncTaskLoader<String>  registerLoader;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         }

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

        telephone = (EditText) v.findViewById(R.id.telephoneEdit);
        password = (EditText) v.findViewById(R.id.passwordEdit);
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
callBackInterface.newUserSubmit();
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

    public AsyncTaskLoader<String> onCreateLoader(int id, Bundle args) {
        AsyncTaskLoader<String> loader=null;
        switch (id) {
            case GlobalVar.LOADER_LOGIN_ID:
                loginLoader = new LoginLoader(getActivity(), args);
                loader = loginLoader;
                break;
            case GlobalVar.LOADER_REGISTER_ID:
                registerLoader = new RegisterLoader(getActivity(), args);
                loader = registerLoader;
                break;

        } return loader;

    }

    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d(GlobalVar.MY_LOG, data);
        GlobalVar.API_TOKEN = data;

    }

    public void onLoaderReset(Loader<String> loader) {
        Log.d(GlobalVar.MY_LOG, "onLoaderReset");
    }


    void testHTTP() {
        try {
            String query = GlobalVar.URL_API + "api/Login";
            // String query ="http://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
            URL searchURL = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection) searchURL.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject login = new JSONObject();
            login.put("Telephone", "0636994493");
            login.put("Password", "123456");

            //  if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
            wr.write(login.toString());
            wr.flush();
            String HttpResult = httpURLConnection.getResponseMessage();
            //   }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
