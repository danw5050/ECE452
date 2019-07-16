package com.goose.tapp;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Map;

public class wifiToggling extends AppCompatActivity implements  Strategy {
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled((Boolean) settings.get("wifi_on"));
    }
}
