package com.tatvasoftassignment.assignment_7.Fragment;

import static com.tatvasoftassignment.assignment_7.Fragment.CityFragment.cityList;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tatvasoftassignment.assignment_7.AsyncTask.CityWeatherForeCastTask;
import com.tatvasoftassignment.assignment_7.BroadCastReceiver.BatteryStateReceiver;
import com.tatvasoftassignment.assignment_7.Database.DbHelper;
import com.tatvasoftassignment.assignment_7.R;
import com.tatvasoftassignment.assignment_7.Utils.Constants;

import java.util.Objects;


public class CityForecastFragment extends Fragment {


    public static TextView txtTempDetail, txtTempMinDetail, txtTempMaxDetail, txtHumidityDetail, txtPressureDetail, txtRainDetail, txtWindDegreeDetail, txtWindSpeedDetail, txtBatteryPercentage;
    public static ImageView iconBattery;
    String cityName;

    public CityForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        assert bundle != null;
        cityName = bundle.getString(Constants.CITY_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(cityName+" "+getString(R.string.weather));
        return inflater.inflate(R.layout.fragment_city_forecast, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        requireActivity().registerReceiver(new BatteryStateReceiver(), new IntentFilter("android.intent.action.BATTERY_CHANGED"));

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.delete_city_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.miDelete) {
            DbHelper db = new DbHelper(getContext());
            db.deleteData(cityName);
            cityList.remove(cityName);

        }
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainActivity, new CityFragment())
                .addToBackStack(null).commit();
        return super.onOptionsItemSelected(item);
    }
}