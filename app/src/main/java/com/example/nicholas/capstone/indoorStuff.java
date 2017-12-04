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
import android.widget.Toast;

import java.util.List;


public class indoorStuff extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_main);

            WifiManager wifiManager = (WifiManager) getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            int numberOfLevels = 5;
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
            String strLevel = ""+level;
            String rsii = String.valueOf(wifiInfo.getRssi());
            TextView closeNode = (TextView) findViewById(R.id.textView2);
            //TextView midNode = (TextView) findViewById(R.id.editText3);
            //TextView farNode = (TextView) findViewById(R.id.editText4);
            //newtext.setText(rsii);

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    wifiManager.getScanResults();
                }
            }
            String scanResultsClose = String.valueOf(wifiManager.getScanResults().get(1));
            //String scanResultsMid = String.valueOf(wifiManager.getScanResults().indexOf(1));
            //String scanResultsEnd = String.valueOf(wifiManager.getScanResults().indexOf(2));
            closeNode.setText(scanResultsClose);
            //midNode.setText(scanResultsMid);
            //farNode.setText(scanResultsEnd);


        }



        public class WifiTest extends Activity {

            public void loadWifiAvailableList(EditText etWifiList){
                WifiManager wifiManager = (WifiManager) getApplicationContext()
                        .getSystemService(Context.WIFI_SERVICE);
                List<ScanResult> results = wifiManager.getScanResults();
                String message = "No results";
                if(results != null){
                    final int size = results.size();
                    if(size == 0)
                        message = "No points found";
                    else{
                        ScanResult bestSignal = results.get(0);
                        etWifiList.setText("");
                        int count = 1;
                        for(ScanResult result:results){
                            etWifiList.append(count++ + ". " + result.SSID + " : " + result.level
                                    + "\n" + result.BSSID + "\n"
                                    + result.capabilities + "\n"
                                    + "\n=======================\n");
                            if(WifiManager.compareSignalLevel(bestSignal.level,result.level)<0){
                                bestSignal = result;
                            }

                        }
                        message = String.format( "%s networks found. %s is the strongest.", size,
                                bestSignal.SSID + ":" + bestSignal.level);
                    }

                }
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }


