package com.tatvasoftassignment.assignment_7.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tatvasoftassignment.assignment_7.AsyncTask.CityTask;
import com.tatvasoftassignment.assignment_7.Database.DbHelper;
import com.tatvasoftassignment.assignment_7.R;

import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {
    public static String bookmarkCity;
    public static String cityName = "";
    public static GoogleMap mGoogleMap;
    public static LatLng mLatLng;
    private DbHelper db;

    public static MarkerOptions markerOptions = new MarkerOptions();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbHelper(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnMap = view.findViewById(R.id.btnMap);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {

            mapFragment.getMapAsync(googleMap -> googleMap.setOnMapClickListener((LatLng latLng) -> {
                mGoogleMap = googleMap;
                mGoogleMap.clear();
                mLatLng = latLng;
                Geocoder geocoder = new Geocoder(MapsFragment.this.getContext(), Locale.getDefault());
                List<Address> addressList;

                try {
                    if (geocoder.getFromLocation(mLatLng.latitude, mLatLng.longitude, 1) != null) {


                        addressList = geocoder.getFromLocation(mLatLng.latitude, mLatLng.longitude, 1);
                        if (addressList.size() > 0 && (addressList.get(0).getLocality()) != null) {
                            cityName = addressList.get(0).getLocality();
                            new CityTask(getContext()).execute(cityName);

                        } else {
                            Toast.makeText(MapsFragment.this.getContext(), MapsFragment.this.getString(R.string.select_proper_location), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MapsFragment.this.getContext(), MapsFragment.this.getString(R.string.select_proper_area), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MapsFragment.this.getContext(), MapsFragment.this.getString(R.string.enable_permission), Toast.LENGTH_SHORT).show();
                }

            }));
        }
        btnMap.setOnClickListener(view1 -> {
            if(bookmarkCity != null) {
                db.insertData(bookmarkCity);
                Toast.makeText(getContext(), bookmarkCity + getString(R.string.book_marked), Toast.LENGTH_SHORT).show();
            }
        });
    }

}