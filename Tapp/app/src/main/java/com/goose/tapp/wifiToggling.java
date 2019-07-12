package com.goose.tapp;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;

public class wifiToggling extends AppCompatActivity {
    public wifiToggling(Context context, boolean wifiOn) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(wifiOn);
    }
}

