package com.tatvasoftassignment.assignment_7.AsyncTask;

import static com.tatvasoftassignment.assignment_7.Fragment.MapsFragment.mGoogleMap;
import static com.tatvasoftassignment.assignment_7.Fragment.MapsFragment.mLatLng;
import static com.tatvasoftassignment.assignment_7.Fragment.MapsFragment.markerOptions;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.tatvasoftassignment.assignment_7.Fragment.MapsFragment;
import com.tatvasoftassignment.assignment_7.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CityTask extends AsyncTask<String, String, String> {


    private final static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final static String API_ID = "fae7190d7e6433ec3a45285ffcf55c86";
    HttpURLConnection connection;
    InputStream inputStream;
    Context ctx;

    public CityTask(Context context) {
        this.ctx = context;
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
        return " ";

    }

    @Override
    protected void onPostExecute(String s) {
        String cityName;
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            cityName = jsonObject.getString("name");

            markerOptions.position(mLatLng);
            mGoogleMap.addMarker(markerOptions);
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 7));
            if(!cityName.isEmpty()) { MapsFragment.bookmarkCity = cityName;}

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ctx, R.string.select_proper_city, Toast.LENGTH_SHORT).show();

        }
    }
}
