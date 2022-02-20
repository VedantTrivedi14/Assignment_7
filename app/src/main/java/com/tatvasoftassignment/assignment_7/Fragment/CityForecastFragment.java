package com.tatvasoftassignment.assignment_7.Fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tatvasoftassignment.assignment_7.AsyncTask.CityWeatherForeCastTask;
import com.tatvasoftassignment.assignment_7.BroadCastReceiver.BatteryStateReceiver;
import com.tatvasoftassignment.assignment_7.R;
import com.tatvasoftassignment.assignment_7.Utils.Constants;


public class CityForecastFragment extends Fragment {


    public static TextView txtTempDetail, txtTempMinDetail, txtTempMaxDetail, txtHumidityDetail, txtPressureDetail, txtRainDetail, txtWindDegreeDetail, txtWindSpeedDetail, txtBatteryPercentage;
    public static ImageView iconBattery;

    public CityForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_city_forecast, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        String cityName = bundle.getString(Constants.CITY_NAME);

        requireActivity().registerReceiver(new BatteryStateReceiver(), new IntentFilter("android.intent.action.BATTERY_CHANGED"));

        TextView txtCityName = view.findViewById(R.id.txtCityName);
        txtCityName.setText(cityName);

        txtTempDetail = view.findViewById(R.id.txtTempDetail);
        txtTempMinDetail = view.findViewById(R.id.txtTempMinDetail);
        txtTempMaxDetail = view.findViewById(R.id.txtTempMaxDetail);
        txtHumidityDetail = view.findViewById(R.id.txtHumidityDetail);
        txtPressureDetail = view.findViewById(R.id.txtPressureDetail);
        txtRainDetail = view.findViewById(R.id.txtRainDetail);
        txtWindDegreeDetail = view.findViewById(R.id.txtWindDegreeDetail);
        txtWindSpeedDetail = view.findViewById(R.id.txtWindSpeedDetail);
        txtBatteryPercentage = view.findViewById(R.id.txtBatteryPercentage);
        iconBattery = view.findViewById(R.id.iconBattery);

        new CityWeatherForeCastTask().execute(cityName);

    }
}