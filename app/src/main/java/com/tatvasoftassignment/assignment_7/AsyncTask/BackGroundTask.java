package com.tatvasoftassignment.assignment_7.AsyncTask;

import android.os.Handler;
import android.os.Looper;

public abstract class BackGroundTask {

    protected Handler localHandler;
    protected Thread localThread;

    public void execute(String cityName) {
        this.localHandler = new Handler(Looper.getMainLooper());
        this.onPreExecute();

        this.localThread = new Thread(() -> {
            String s = BackGroundTask.this.doInBackground(cityName);

            BackGroundTask.this.localHandler.post(() -> BackGroundTask.this.onPostExecute(s));
        });
        this.localThread.start();
    }



    protected void onPreExecute() {}

    protected abstract String doInBackground(String... strings);

    protected void onPostExecute(String s) {}
}
