package com.goose.tapp;

import android.content.Context;
import android.app.NotificationManager;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;


import java.util.Map;

public class DoNotDisturb extends AppCompatActivity implements Strategy {
    @Override
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings) {
        Boolean status = false;
        for(Map.Entry<String, Object>entry : settings.entrySet()){
            if(entry.getKey().equals("dnd")){
                String [] separated = entry.getValue().toString().split("=");
                status = Boolean.parseBoolean(separated[1].substring(0, separated[1].length() - 1));
            }
        }

        if(status){
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Check if the notification policy access has been granted for the app.
            if (!mNotificationManager.isNotificationPolicyAccessGranted()) {
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);
            }
        }
    }
}
