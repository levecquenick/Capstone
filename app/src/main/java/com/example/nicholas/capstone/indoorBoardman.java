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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.List;

import static android.net.wifi.WifiManager.*;


public class indoorBoardman extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        TextView accessPointInfo = (TextView) findViewById(R.id.textView);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                wifiManager.getScanResults();
            }
        }

        String scanResultsClose = " ";
        int x = 0;

        for (int i = 0; i < wifiManager.getScanResults().size() && x <=2; i++) {

            String testNextwork = String.valueOf(wifiManager.getScanResults().get(i).SSID);
            if (testNextwork.contains("tempest")) {
                scanResultsClose += String.valueOf(wifiManager.getScanResults().get(i) + "\n" +
                        calculateDistance(wifiManager.getScanResults().get(i).level, wifiManager.getScanResults().get(i).frequency)) + "\n";
                x++;
            }

        }



        accessPointInfo.setText(String.valueOf(scanResultsClose));



    }



    public double calculateDistance(double signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }




}


