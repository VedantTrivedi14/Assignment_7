package com.tatvasoftassignment.assignment_7.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.tatvasoftassignment.assignment_7.Fragment.CityForecastFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CityWeatherForeCastTask extends AsyncTask<String, String, String> {

    static boolean isPresent = true;
    private final static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final static String API_ID = "fae7190d7e6433ec3a45285ffcf55c86";
    HttpURLConnection connection;
    InputStream inputStream;
    public CityWeatherForeCastTask() {
    }

    @Override
    protected String doInBackground(String... strings) {
        connection = null;
        inputStream = null;
        try {
            URL url = new URL(String.format("%s%s&APPID=%s", BASE_URL, strings[0], API_ID));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            StringBuilder stringBuilder = new StringBuilder();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            inputStream.close();
            connection.disconnect();

            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String temp = "", tempMax = "", tempMin = "", humidity = "", pressure = "", rain = "", windDegree = "", windSpeed = "";

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject object = jsonObject.getJSONObject("main");
            temp = object.getString("temp");
            tempMax = object.getString("temp_max");
            tempMin = object.getString("temp_min");
            humidity = object.getString("humidity");
            pressure = object.getString("pressure");
            rain = jsonObject.getJSONObject("clouds").getString("all");
            windDegree = jsonObject.getJSONObject("wind").getString("speed");
            windSpeed = jsonObject.getJSONObject("wind").getString("deg");

            isPresent = true;

        } catch (JSONException e) {
            e.printStackTrace();
            isPresent = false;
            Log.d("Weather", String.valueOf(e));
        }

        CityForecastFragment.txtTempDetail.setText(temp);
        CityForecastFragment.txtTempMaxDetail.setText(tempMax);
        CityForecastFragment.txtTempMinDetail.setText(tempMin);
        CityForecastFragment.txtHumidityDetail.setText(humidity);
        CityForecastFragment.txtPressureDetail.setText(pressure);
        CityForecastFragment.txtRainDetail.setText(rain);
        CityForecastFragment.txtWindDegreeDetail.setText(windDegree);
        CityForecastFragment.txtWindSpeedDetail.setText(windSpeed);

    }

}
