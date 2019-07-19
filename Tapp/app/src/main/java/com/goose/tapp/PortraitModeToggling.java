package com.goose.tapp;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Map;

public class PortraitModeToggling  extends AppCompatActivity implements Strategy  {
    @Override
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings) {
        Boolean status = false;

        for(Map.Entry<String, Object>entry : settings.entrySet()){
            if(entry.getKey().equals("portrait")){
                String [] separated = entry.getValue().toString().split("=");
                status = Boolean.parseBoolean(separated[1].substring(0, separated[1].length() - 1));
            }
        }

        if(status){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
