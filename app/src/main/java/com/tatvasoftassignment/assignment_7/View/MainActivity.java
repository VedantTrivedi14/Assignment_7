package com.tatvasoftassignment.assignment_7.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tatvasoftassignment.assignment_7.Fragment.CityFragment;
import com.tatvasoftassignment.assignment_7.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.MainActivity, new CityFragment())
                .commit();
    }
}