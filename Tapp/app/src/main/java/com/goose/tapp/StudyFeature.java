package com.goose.tapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.Map;

public class StudyFeature extends AppCompatActivity implements Strategy {
    @Override
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings) {
        String Question = "What is the difference?";
        String Answer = "I don't know";
        Boolean status = false;

        for(Map.Entry<String, Object>entry : settings.entrySet()){
            if(entry.getKey().equals("StudyECE452")){
                String [] separated = entry.getValue().toString().split("=");
                status = Boolean.parseBoolean(separated[1].substring(0, separated[1].length() - 1));
            }
        }

        if(status){

        }
    }
}
