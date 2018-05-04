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

import com.google.android.gms.maps.model.LatLng;

import java.lang.Math;


import java.util.ArrayList;
import java.util.List;

import static android.net.wifi.WifiManager.*;
import static java.lang.Math.round;



/*
this class uses the wifi info for boardman hall its the same
as estabrooke, I was unable to find the correct BSSID for the 3 points
in boardman the lat and lng of each point is correct though

 */
public class indoorBoardman extends AppCompatActivity {
    //the 3 wifi access point BSSIDs used to get signal
    String accessPoint1BSSID = "18:9c:5d:d5:b4:30";
    String accessPoint2BSSID = "18:9c:5d:ef:b2:80";
    String accessPoint3BSSID = "5c:a4:8a:b3:41:30";

    //the lat and lng of each access point
    double point1Lat = 44.90206;
    double point1Lng = 68.66882;
    double point2Lat = 44.90222;
    double point2Lng = 68.66911;
    double point3Lat = 44.90238;
    double point3Lng = 68.66909;

    //make em latlng points cuz why not
    LatLng point1Loc = new LatLng(point1Lat,point1Lng);
    LatLng point2Loc = new LatLng(point2Lat,point2Lng);
    LatLng point3Loc = new LatLng(point3Lat,point3Lng);

    //for triangulation purposes
    double error = 0.00001;

    //array lists to hold possible locations for user
    ArrayList<LatLng> point1Dist = new ArrayList<>();
    ArrayList<LatLng> point3Dist = new ArrayList<>();
    ArrayList<LatLng> point2Dist = new ArrayList<>();
    ArrayList<LatLng> userLoc = new ArrayList<LatLng>();
    //I only deal with tempest networks
    ArrayList<ScanResult> tempestOnly = new ArrayList<android.net.wifi.ScanResult>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        TextView accessPointInfo = (TextView) findViewById(R.id.textView);


        //I put everything in this method so it would update when I hit the button
        setUpEverything();


    }


    //calculates the distance from a wifi access point
    public double calculateDistance(double signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }

    //this bad boy does everything, it would normally be in the onCreate method but i want it to
    //update when i hit a button so I threw it into a method
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
        //update button
        updateDistances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUpEverything();
            }
        });
        //early tests to switch between activities
        switchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switchActivity();
            }
        });

        String scanResultsClose = " ";

        double distance;

        double shortest = 99.0;

        for(int x = 0; x <wifiManager.getScanResults().size(); x++){
            String BSSID = String.valueOf(wifiManager.getScanResults().get(x).BSSID);
            String SSID = String.valueOf(wifiManager.getScanResults().get(x).SSID);

            if(wifiManager.getScanResults().get(x).SSID.contains("tempest")){
                tempestOnly.add(wifiManager.getScanResults().get(x));
            }
        }


        for (int i = 0; i < tempestOnly.size(); i++) {
            distance = calculateDistance(tempestOnly.get(i).level, tempestOnly.get(i).frequency);



            String BSSID = String.valueOf(tempestOnly.get(i).BSSID);



            if (BSSID.contains(accessPoint1BSSID))
            {
                // scanResultsClose += String.valueOf(wifiManager.getScanResults().get(i) + "\n" + distance) + "\n";

                makeCircle(point1Loc, distance,point1Dist);
            }
            else {
                if (BSSID.contains(accessPoint2BSSID)) {
                    // scanResultsClose += String.valueOf(wifiManager.getScanResults().get(i) + "\n" + distance) + "\n";

                    makeCircle(point2Loc, distance, point2Dist);
                } else {
                    if (BSSID.contains(accessPoint3BSSID))
                    {
                        // scanResultsClose += String.valueOf(wifiManager.getScanResults().get(i) + "\n" + distance) + "\n";

                        makeCircle(point3Loc, distance, point3Dist);
                    }
                }
            }
        }


        while(userLoc.size() == 0) {
            userLoc = getLoc(point1Dist, point2Dist, point3Dist, error);
            error += 0.00001;
        }
        double totalLat = 0;
        double totalLng = 0;
        for(int x = 0; x<userLoc.size(); x++){
            totalLat += userLoc.get(x).latitude;
            totalLng += userLoc.get(x).longitude;

        }

        totalLat /= userLoc.size();
        totalLng /= userLoc.size();
        for(int i = 0; i<point1Dist.size(); i++) {
            double modifiedLat = Math.round(point1Dist.get(i).latitude *1000000.0)/1000000.0;
            scanResultsClose += String.valueOf(point2Dist.get(i).latitude+ " " + point2Dist.get(i).latitude+ " " + point3Dist.get(i).latitude+ "\n");
        }
        accessPointInfo.setText(String.valueOf(userLoc));
        //accessPointInfo.setText(String.valueOf(scanResultsClose));

    }

    public void switchActivity(){
        Intent intent = new Intent(this, indoorBoardman.class);
        startActivity(intent);
    }

    public double convertLatLong(double distance){
        double latLong = distance/30.8;
        return latLong;
    }

    /*
    uses the radius and the latLng of the center to find a series of points in a circle around
    the center
    the center is the location of a wifi access point
     */
    private void makeCircle(LatLng center, double radius, ArrayList<LatLng> points)
    {

        double EARTH_RADIUS = 6378100.0;
        // Convert to radians.
        double lat = center.latitude * Math.PI / 180.0;
        double lon = center.longitude * Math.PI / 180.0;

        for (double t = 0; t <= Math.PI * 2; t += 0.13)
        {
            // y
            double latPoint = lat + (radius / EARTH_RADIUS) * Math.sin(t);
            // x
            double lonPoint = lon + (radius / EARTH_RADIUS) * Math.cos(t) / Math.cos(lat);

            // saving the location on circle as a LatLng point
            LatLng point =new LatLng(latPoint * 180.0 / Math.PI, lonPoint * 180.0 / Math.PI);


            points.add(point);

        }
    }

    /*this method compares 3 arraylists against each other
    if the same lat and long is in all three lists then that is the user's location
    if the same isn't in all three lists it finds the difference between both lat and long and compares
    it to the margin for error variable above. if the error is larger than the difference it considers them
    the same
    */
    public ArrayList<LatLng> getLoc(ArrayList<LatLng> distances1,ArrayList<LatLng> distances2,ArrayList<LatLng> distances3, double error) {
        ArrayList<LatLng> location = new ArrayList<LatLng>();
        for (int x = 0; x < distances1.size(); x++) {
            for (int i = 0; i < distances2.size(); i++) {
                for (int a = 0; a < distances3.size(); a++) {
                    double distanceError1and2Lat = Math.abs(distances1.get(x).latitude - distances2.get(i).latitude);
                    double distanceError1and2Lng = Math.abs(distances1.get(x).longitude - distances2.get(i).longitude);

                    double distanceError2and3Lat = Math.abs(distances2.get(i).latitude - distances3.get(a).latitude);
                    double distanceError2and3Lng = Math.abs(distances2.get(i).longitude - distances3.get(a).longitude);

                    if((distanceError1and2Lat <= error && distanceError2and3Lat <= error)
                            && distanceError1and2Lng <= error && distanceError2and3Lng <= error){
                        location.add(distances3.get(a));
                        distances1.remove(distances1.get(x));
                        distances2.remove(distances2.get(i));
                        distances3.remove(distances3.get(a));
                        break;
                    }
                }
            }

        }

        return location;


    }

}




