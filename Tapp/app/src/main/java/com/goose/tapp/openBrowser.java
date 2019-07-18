package com.goose.tapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import java.util.Map;

public class openBrowser extends AppCompatActivity implements Strategy{
    @Override
    public void doTheSpecifiedTask(Context context, Map<String, Object> settings) {
        String link = "";
        for(Map.Entry<String, Object>entry : settings.entrySet()){
            if(entry.getKey().equals("browser")){
                String [] separated = entry.getValue().toString().split("=");
                link = separated[1].substring(0, separated[1].length() - 1);
            }
        }

        if(link == null || link.length() == 0 || link.isEmpty()){
            return;
        }
        // Add https otherwise the code fails to run
        if (!link.startsWith("http://") && !link.startsWith("https://")){
            link = "https://" + link;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }
}