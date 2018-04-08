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
import static android.view.View.*;


public class indoorEstabrooke extends AppCompatActivity {
        String accessPoint1BSSID = "d8:b1:90:b3:3f:80";
        String accessPoint2BSSID = "d8:b1:90:b3:49:d0";
        String accessPoint3BSSID = "d8:b1:90:d3:48:f0";

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_main);

            WifiManager wifiManager = (WifiManager) getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            TextView accessPointInfo = (TextView) findViewById(R.id.textView);

            setUpEverything();












            /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    wifiManager.getScanResults();
                }
            }

            String scanResultsClose = " ";


                for (int i = 0; i < wifiManager.getScanResults().size(); i++) {

                    String BSSID = String.valueOf(wifiManager.getScanResults().get(i).BSSID);
                    if (BSSID.contains(accessPoint1BSSID) || BSSID.contains(accessPoint2BSSID) || BSSID.contains(accessPoint3BSSID)){
                        scanResultsClose += String.valueOf(wifiManager.getScanResults().get(i) + "\n" +
                                calculateDistance(wifiManager.getScanResults().get(i).level, wifiManager.getScanResults().get(i).frequency)) + "\n";
                    }

                }



                accessPointInfo.setText(String.valueOf(scanResultsClose));
                */



        }



    public double calculateDistance(double signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }

    public void updateDistances(){

    }

    public void setUpEverything(){
        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        TextView accessPointInfo = (TextView) findViewById(R.id.textView);
        Button updateDistances = (Button) findViewById(R.id.button6);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                wifiManager.getScanResults();
            }
        }

        updateDistances.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                setUpEverything();
            }
        });

        String scanResultsClose = " ";


        for (int i = 0; i < wifiManager.getScanResults().size(); i++) {

            String BSSID = String.valueOf(wifiManager.getScanResults().get(i).BSSID);
            if (BSSID.contains(accessPoint1BSSID) || BSSID.contains(accessPoint2BSSID) || BSSID.contains(accessPoint3BSSID)){
                scanResultsClose += String.valueOf(wifiManager.getScanResults().get(i) + "\n" +
                        calculateDistance(wifiManager.getScanResults().get(i).level, wifiManager.getScanResults().get(i).frequency)) + "\n";
            }

        }



        accessPointInfo.setText(String.valueOf(scanResultsClose));
    }





    }


