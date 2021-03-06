package com.goose.tapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.Map;

public class OpenExternalApplication extends AppCompatActivity implements  Strategy {
    @Override
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings) {
        String application = "";
        for(Map.Entry<String, Object>entry : settings.entrySet()){
            if(entry.getKey().equals("application")){
                String [] separated = entry.getValue().toString().split("appPackage");
                String [] separated2 = separated[1].split("=");
                String str1 = separated2[1].substring(0, separated2[1].length() - 1);
                String [] separated3 =  str1.split(",");
                application =  separated3[0];

                if(application == null || application.length() == 0 || application.isEmpty()){
                    return;
                }

                try{
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(application);
                    context.startActivity(intent);
                }
                catch (Exception ex){
                    // Show a toast message
                }
            }
        }
    }
}
