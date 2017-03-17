package com.susyimes.bulbroom;

import java.util.Map;

import wei.mark.standout.StandOutWindow;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;


public class MainActivity extends Activity {

    public static float density, width, height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent serviceIntent = new Intent();
        serviceIntent.setAction("wei.mark.standout.StandOutWindow");
        this.stopService(serviceIntent);
        density = getResources().getDisplayMetrics().density;
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();

        new Thread(new Runnable() {

            @Override
            public void run() {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, ?> keys = prefs.getAll();

                boolean none = true;
                for (Map.Entry<String, ?> entry : keys.entrySet()) {
                    try {
                        if (entry.getKey().contains("_id") && !entry.getValue().equals("")) {
                            none = false;
                            Log.d("startup", entry.getKey());
                            StandOutWindow.show(MainActivity.this, FloatingSticky.class, Integer.parseInt(entry.getKey().replace("_id", "")));
                            Thread.sleep(1500);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (none) {
                    StandOutWindow.show(MainActivity.this, FloatingSticky.class, StandOutWindow.DEFAULT_ID);
                }
            }

        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }



}