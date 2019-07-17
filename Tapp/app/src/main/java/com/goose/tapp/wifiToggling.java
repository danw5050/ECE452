package com.goose.tapp;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Map;

public class wifiToggling extends AppCompatActivity implements  Strategy {
    @Override
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings) {
        Boolean status = false;
        for(Map.Entry<String, Object>entry : settings.entrySet()){
            if(entry.getKey().equals("wifi")){
                String [] separated = entry.getValue().toString().split("=");
                status = Boolean.parseBoolean(separated[1].substring(0, separated[1].length() - 1));
            }
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(status);
    }
}
