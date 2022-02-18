package com.tatvasoftassignment.assignment_7.Fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoftassignment.assignment_7.Adapter.RecyclerViewAdapter;
import com.tatvasoftassignment.assignment_7.Database.DbHelper;
import com.tatvasoftassignment.assignment_7.R;

import java.util.ArrayList;


public class CityFragment extends Fragment {

    private final ArrayList<String> cityList = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private DbHelper db;
    private TextView txtMassage;
    private RecyclerView recCityList;

    public CityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        db = new DbHelper(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        cityList.clear();
        setAdapter();
        setDataToCityList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recCityList = view.findViewById(R.id.recCityList);
        txtMassage = view.findViewById(R.id.txtMassage);
        setAdapter();
        setDataToCityList();
    }


    public void setDataToCityList() {
        Cursor data = db.getData();
        if (data.getCount() == 0) {
            txtMassage.setVisibility(View.VISIBLE);

        } else {
            txtMassage.setVisibility(View.INVISIBLE);
            while (data.moveToNext()) {
                cityList.add(data.getString(1));
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void setAdapter() {
        adapter = new RecyclerViewAdapter(cityList);
        recCityList.setAdapter(adapter);
        recCityList.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        recCityList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_location_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainActivity, new MapsFragment())
                .addToBackStack(null).commit();
        return super.onOptionsItemSelected(item);
    }
}