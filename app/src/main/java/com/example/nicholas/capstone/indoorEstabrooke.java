package com.example.nicholas.capstone;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;

import static android.view.View.*;


public class indoorEstabrooke extends AppCompatActivity {
        String accessPoint1BSSID = "18:9c:d5:5d:b4:30";
        String accessPoint2BSSID = "5c:a4:8a:b3:41:30";
        String accessPoint3BSSID = "18:9c:5d:ef:b2:80";

        double point1Lat = 44.89638889;
        double point1Lng = 68.66972222;
        double point2Lat = 44.89638889;
        double point2Lng = 68.67;
        double point3Lat = 44.89611111;
        double point3Lng = 68.67;

        LatLng point1Loc = new LatLng(point1Lat,point1Lng);
        LatLng point2Loc = new LatLng(point2Lat,point2Lng);
        LatLng point3Loc = new LatLng(point3Lat,point3Lng);

        double locLat;
        double locLng;

        ArrayList<LatLng> point1Dist = new ArrayList<>();
        ArrayList<LatLng> point3Dist = new ArrayList<>();
        ArrayList<LatLng> point2Dist = new ArrayList<>();
        ArrayList<LatLng> userLoc = new ArrayList<LatLng>();


        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_main);

            WifiManager wifiManager = (WifiManager) getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            TextView accessPointInfo = (TextView) findViewById(R.id.textView);



            setUpEverything();


        }



    public double calculateDistance(double signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }


    public void setUpEverything(){
        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        TextView accessPointInfo = (TextView) findViewById(R.id.textView);
        Button updateDistances = (Button) findViewById(R.id.button6);
        final Button switchActivity = (Button) findViewById(R.id.button2);




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

        switchActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                switchActivity();
            }
        });

        String scanResultsClose = " ";

        double distance;

        double shortest = 99.0;


        for (int i = 0; i < wifiManager.getScanResults().size(); i++) {
            distance = calculateDistance(wifiManager.getScanResults().get(i).level, wifiManager.getScanResults().get(i).frequency);



            String BSSID = String.valueOf(wifiManager.getScanResults().get(i).BSSID);
            String SSID = String.valueOf(wifiManager.getScanResults().get(i).SSID);
            if (BSSID.contains(accessPoint1BSSID) || BSSID.contains(accessPoint2BSSID) || BSSID.contains(accessPoint3BSSID))
            {
               // scanResultsClose += String.valueOf(wifiManager.getScanResults().get(i) + "\n" + distance) + "\n";

                makeCircle(point1Loc, distance,point1Dist);
            }
            if (BSSID.contains(accessPoint2BSSID))
            {
                // scanResultsClose += String.valueOf(wifiManager.getScanResults().get(i) + "\n" + distance) + "\n";

                makeCircle(point2Loc, distance,point2Dist);
            }
            if (BSSID.contains(accessPoint3BSSID))
            {
                // scanResultsClose += String.valueOf(wifiManager.getScanResults().get(i) + "\n" + distance) + "\n";

                makeCircle(point3Loc, distance,point3Dist);
            }
        }




        userLoc.add(getLoc(point1Dist,point2Dist,point3Dist, locLat, locLng).get(0));
        for(int i = 0; i<point1Dist.size(); i++) {
            double modifiedLat = Math.round(point1Dist.get(i).latitude *1000000.0)/1000000.0;
            scanResultsClose += String.valueOf(modifiedLat+ "\n" + point1Dist.get(i).longitude + "\n");
        }
        accessPointInfo.setText(String.valueOf(userLoc.get(0)));
    }

    public void switchActivity(){
        Intent intent = new Intent(this, indoorBoardman.class);
        startActivity(intent);
    }

    public double convertLatLong(double distance){
        double latLong = distance/30.8;
        return latLong;
    }

    private void makeCircle(LatLng centre, double radius, ArrayList<LatLng> points)
    {

        double EARTH_RADIUS = 6378100.0;
        // Convert to radians.
        double lat = centre.latitude * Math.PI / 180.0;
        double lon = centre.longitude * Math.PI / 180.0;

        for (double t = 0; t <= Math.PI * 2; t += 0.13)
        {
            // y
            double latPoint = lat + (radius / EARTH_RADIUS) * Math.sin(t);
            // x
            double lonPoint = lon + (radius / EARTH_RADIUS) * Math.cos(t) / Math.cos(lat);

            // saving the location on circle as a LatLng point
            LatLng point =new LatLng(latPoint * 180.0 / Math.PI, lonPoint * 180.0 / Math.PI);


            // now here note that same point(lat/lng) is used for marker as well as saved in the ArrayList
            points.add(point);

        }
    }

    public ArrayList<LatLng> getLoc(ArrayList<LatLng> distances1,ArrayList<LatLng> distances2,ArrayList<LatLng> distances3, double locLat, double locLng) {
        ArrayList<LatLng> location = new ArrayList<LatLng>();

        for (int x = 0; x < distances1.size(); x++) {
            double roundedLat1 = Math.round(distances1.get(x).latitude * 100000.0) / 100000.0;
            double roundedLng1 = Math.round(distances1.get(x).longitude * 100000.0) / 100000.0;

            for (int i = 0; i < distances2.size(); i++) {
                double roundedLat2 = Math.round(distances2.get(i).latitude * 100000.0) / 100000.0;
                double roundedLng2 = Math.round(distances2.get(i).longitude * 100000.0) / 100000.0;
                for (int a = 0; a < distances3.size(); a++) {
                    double roundedLat3 = Math.round(distances3.get(a).latitude * 100000.0) / 100000.0;
                    double roundedLng3 = Math.round(distances3.get(a).longitude * 100000.0) / 100000.0;
                    if((roundedLat1 == roundedLat2 && roundedLng1 == roundedLng2)&&
                            (roundedLat2 == roundedLat3 && roundedLng2 == roundedLng3)){
                        location.add(distances1.get(x));
                    }
                }
            }

        }
        return location;

    }







    }


