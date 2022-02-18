package com.tatvasoftassignment.assignment_7.Adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoftassignment.assignment_7.Database.DbHelper;
import com.tatvasoftassignment.assignment_7.Fragment.CityForecastFragment;
import com.tatvasoftassignment.assignment_7.R;
import com.tatvasoftassignment.assignment_7.Utils.Constants;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    ArrayList<String> cityList;

    public RecyclerViewAdapter(ArrayList<String> cityList) {
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.city_list_item_view, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        holder.txtCityName.setText(cityList.get(position));
        holder.btnImgDelete.setOnClickListener(v -> {
            DbHelper db = new DbHelper(v.getContext());
            db.deleteData(cityList.get(position));
            cityList.remove(cityList.get(position));
            notifyDataSetChanged();
        });
        holder.itemView.setOnClickListener(v -> {
            CityForecastFragment fragment = new CityForecastFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.CITY_NAME, cityList.get(position));
            fragment.setArguments(bundle);
            ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.MainActivity, fragment)
                    .addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtCityName;
        ImageButton btnImgDelete;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCityName = itemView.findViewById(R.id.txtCityName);
            btnImgDelete = itemView.findViewById(R.id.btnImgDelete);
        }
    }


}
