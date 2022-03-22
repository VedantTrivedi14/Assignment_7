package com.tatvasoftassignment.assignment_7.BroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.tatvasoftassignment.assignment_7.Fragment.CityForecastFragment;
import com.tatvasoftassignment.assignment_7.R;

public class BatteryStateReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryLevel = level * 100 / (float) scale;
        if (batteryLevel <= 5) {
            CityForecastFragment.iconBattery.setImageResource(R.drawable.ic_baseline_battery_alert_24);
        } else if (batteryLevel > 5 && batteryLevel <= 20) {
            CityForecastFragment.iconBattery.setImageResource(R.drawable.ic_baseline_battery_1_bar_24);
        } else if (batteryLevel > 20 && batteryLevel <= 40) {
            CityForecastFragment.iconBattery.setImageResource(R.drawable.ic_baseline_battery_3_bar_24);
        } else if (batteryLevel > 40 && batteryLevel <= 60) {
            CityForecastFragment.iconBattery.setImageResource(R.drawable.ic_baseline_battery_4_bar_24);
        } else if(batteryLevel > 60 && batteryLevel <= 80){
            CityForecastFragment.iconBattery.setImageResource(R.drawable.ic_baseline_battery_5_bar_24);
        }else{
            CityForecastFragment.iconBattery.setImageResource(R.drawable.ic_baseline_battery_full_24);
        }
        CityForecastFragment.txtBatteryPercentage.setText(String.valueOf(batteryLevel));
    }
}