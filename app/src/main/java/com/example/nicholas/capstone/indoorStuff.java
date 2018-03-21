package com.example.nicholas.capstone;

import android.*;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;


import java.util.List;

import static android.net.wifi.WifiManager.*;


public class indoorStuff extends AppCompatActivity {
        double signalLevel;
        double frequency;
        double result;
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_main);

            WifiManager wifiManager = (WifiManager) getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            TextView accessPointInfo = (TextView) findViewById(R.id.textView2);

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    wifiManager.getScanResults();
                }
            }
            String scanResultsClose = String.valueOf(wifiManager.getScanResults().get(0) + "\n" +
                    wifiManager.getScanResults().get(1) + "\n" +
                    wifiManager.getScanResults().get(2));

            signalLevel = wifiManager.getScanResults().get(0).level;
            frequency = wifiManager.getScanResults().get(0).frequency;

            result = calculateDistance(signalLevel, frequency);


            accessPointInfo.setText(String.valueOf(result));


        }

    public double calculateDistance(double signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }


    }


