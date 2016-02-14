package com.isolomonik.trisho.utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.isolomonik.trisho.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadDataService extends IntentService {

    public LoadDataService() {
        super("LoadDataService");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String json = "";
        String query = null;
        try {
            query =GlobalVar.URL_API+"/api/Login" ;

            URL searchURL = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection) searchURL.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    json += line;
                }

                bufferedReader.close();
            }

            JSONObject jsonResponse = new JSONObject(json);

          //  JSONArray weatherArray = jsonWeather.getJSONArray("list");

//            for (int i = 0; i < weatherArray.length(); i++) {
//                WeatherData weather = new WeatherData();
//                weather.setDt(weatherArray.getJSONObject(i).getString("dt"));
//                weather.setDate(weatherArray.getJSONObject(i).getString("dt_txt"));
//                weather.setIcon(weatherArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon"));
//                weather.setTemp(weatherArray.getJSONObject(i).getJSONObject("main").getDouble("temp"));
//                weather.setDescription(weatherArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"));
//                weather.setDeg(weatherArray.getJSONObject(i).getJSONObject("wind").getInt("deg"));
//                weather.setSpeed(weatherArray.getJSONObject(i).getJSONObject("wind").getDouble("speed"));
//                weather.setHumidity(weatherArray.getJSONObject(i).getJSONObject("main").getInt("humidity"));
//                weather.setPressure(weatherArray.getJSONObject(i).getJSONObject("main").getDouble("pressure"));
//
//                realm.copyToRealmOrUpdate(weather);
//            }
//            realm.commitTransaction();
            Log.v(GlobalVar.MY_LOG, "update_ok");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
