package com.goose.tapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

public class StudyFeature extends AppCompatActivity implements Strategy {
    @Override
    public void doTheSpecifiedTask(final Context context, Map<String, Object> settings) {
        Boolean status = false;

        for(Map.Entry<String, Object>entry : settings.entrySet()){
            if(entry.getKey().equals("StudyECE452")){
                String [] separated = entry.getValue().toString().split("=");
                status = Boolean.parseBoolean(separated[1].substring(0, separated[1].length() - 1));
            }
        }

        if(status){
            Intent myIntent = new Intent(context, StudyFeaturesActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myIntent);
        }
    }
}
