package com.example.nicholas.capstone;

import android.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.lang.Math;


import java.util.ArrayList;
import java.util.List;

import static android.net.wifi.WifiManager.*;
import static java.lang.Math.round;


public class indoorBoardman extends AppCompatActivity {
    public wayPoint basementPoint1 = new wayPoint(66,540);
    public wayPoint basementPoint2 = new wayPoint(340, 540);
    public wayPoint basementPoint3 = new wayPoint(340, 130);

    classroom room01 = new classroom(66, 540, "room01");
    classroom room02 = new classroom(66, 460, "room08");
    classroom room03 = new classroom(160, 540, "room18");
    classroom room04 = new classroom(160, 540, "room09");
    classroom room05 = new classroom(275, 540, "room15");
    classroom room06 = new classroom(340, 495, "room25");
    classroom room07 = new classroom(340, 400, "room26");
    classroom room08 = new classroom(340, 400, "room29");
    classroom room09 = new classroom(340, 305, "room30");
    classroom room10 = new classroom(340, 275, "room32");
    classroom room11 = new classroom(340, 205, "room34");
    classroom room12 = new classroom(340, 540, "room17");
    classroom room13 = new classroom(375, 130, "room40");
    ArrayList<wayPoint> basementWaypoints = new ArrayList<wayPoint>();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        basementWaypoints.add(basementPoint1);
        basementWaypoints.add(basementPoint2);
        basementWaypoints.add(basementPoint3);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        TextView accessPointInfo = findViewById(R.id.textView);
        final Button switchActivity = findViewById(R.id.button2);



        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                wifiManager.getScanResults();
            }
        }

        String scanResultsClose = " ";
        int x = 0;
        double distance;
        for (int i = 0; i < wifiManager.getScanResults().size() && x<=2; i++) {

            String testNetwork = String.valueOf(wifiManager.getScanResults().get(i).SSID);
            if (testNetwork.contains("tempest")) {
                distance = calculateDistance(wifiManager.getScanResults().get(i).level, wifiManager.getScanResults().get(i).frequency);
                scanResultsClose += String.valueOf(wifiManager.getScanResults().get(i) + "\n" +
                        convertLatLong(distance) + "\n" +
                        distance) + "\n";
                x++;
            }

        }

        switchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switchActivity();
            }
        });



        accessPointInfo.setText(String.valueOf(scanResultsClose));



    }



    public double calculateDistance(double signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }

    public double convertLatLong(double distance){
        double latLong = distance/30.8;
        return latLong;
    }

    public void switchActivity(){
        finish();
    }




}




