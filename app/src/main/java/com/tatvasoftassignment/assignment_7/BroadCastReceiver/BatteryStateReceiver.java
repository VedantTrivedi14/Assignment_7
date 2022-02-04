package com.tatvasoftassignment.assignment_7.BroadCastReceiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.tatvasoftassignment.assignment_7.Fragment.CityForecastFragment;
import com.tatvasoftassignment.assignment_7.R;

public class BatteryStateReceiver extends BroadcastReceiver {


    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryLevel = level * 100 / (float) scale;
        if (batteryLevel <= 5) {
            CityForecastFragment.iconBattery.setImageResource(R.drawable.ic_battery_5_);
        } else if (batteryLevel > 5 && batteryLevel <= 20) {
            CityForecastFragment.iconBattery.setImageResource(R.drawable.ic_battery_20_);
        } else if (batteryLevel > 20 && batteryLevel <= 50) {
            CityForecastFragment.iconBattery.setImageResource(R.drawable.ic_battery_50_);
        } else if (batteryLevel > 50 && batteryLevel <= 80) {
            CityForecastFragment.iconBattery.setImageResource(R.drawable.ic_battery_80_);
        } else {
            CityForecastFragment.iconBattery.setImageResource(R.drawable.ic_battery_100_);
        }
        CityForecastFragment.txtBatteryPercentage.setText(String.valueOf(batteryLevel));
    }
}