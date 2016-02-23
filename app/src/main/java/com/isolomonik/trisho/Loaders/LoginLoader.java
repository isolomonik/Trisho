package com.isolomonik.trisho.Loaders;

//import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.isolomonik.trisho.utils.GlobalVar;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class LoginLoader extends AsyncTaskLoader<String> {
    private String telephone;
    private  String passw;
    private String token="";



    public LoginLoader(Context context, Bundle args) {
        super(context);
        Log.d(GlobalVar.MY_LOG, hashCode() + " create LoginAsyncLoader");
        if (args != null) {
            telephone = args.getString("Telephone");
            passw=args.getString("Password");
        }

    }

    @Override
      public String loadInBackground() {
        Log.d(GlobalVar.MY_LOG, hashCode() + " loadInBackground start");
//
//        //  todo restapi
//        LoginModel loginModel = new LoginModel("0636994493","123456");
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(GlobalVar.URL_API)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(new OkHttpClient())
//                .build();
//        RESTRetrofitInterface rest = retrofit.create(RESTRetrofitInterface.class);
//        Call<String> call=rest.loginToken(loginModel);
//       //Log.v("my_log", "REQUEST:   " + loginModel.toJson());
//       try {
//           Response<String> response = call.execute();
//           //Log.v(GlobalVar.MY_LOG, "получилось"+response.body().toString());
//           //token=response.body().toString();
//       }catch (IOException e){
//           e.printStackTrace();
//           Log.v(GlobalVar.MY_LOG, "не получилось");
//       }
//
//       // end //


OKHttp();
        return token;
    }

    @Override
    public void deliverResult(String data) {
        if (isReset()) {
           token="";
            return;
        }
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (!token.equals("")) {
            deliverResult(token);
        }
        if (takeContentChanged() || token.equals("")) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(String data) {
       token="";
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

       token="";
    }

    void OKHttp(){
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        try {

            JSONObject login = new JSONObject();
            login.put("Telephone", telephone);
            login.put("Password", passw);

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, login.toString());
            Request request = new Request.Builder()
                    .url(GlobalVar.URL_API+"api/Login")
                    .post(body)
                    .build();
            okhttp3.Response response = client.newCall(request)
                    .execute();

       String resp =  response.body().string();
            token =resp.replaceAll("\"", "");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
